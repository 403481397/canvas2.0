package sample;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import javax.xml.soap.Text;
import java.util.LinkedList;

public class Main extends Application {

    private Canvas canvas = new Canvas(3000, 1000);
    private GraphicsContext gc = canvas.getGraphicsContext2D();
    private Group root = new Group();
    public static LinkedList<Task> taskList=new LinkedList<>();//任务队列
    public static LinkedList<Task> selList=new LinkedList<>();//选中队列
    private String time1;
    private String time2;
    private int fnow=0;
    private int flast=0;
    private double selectX=0;
    private double selecty=0;

    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle("gantt2.0");
        initData();
        initWin();
        primaryStage.setScene(new Scene(root, 850, 600));
        primaryStage.show();
    }

    public void initWin(){//初始化界面
        gc.strokeLine(100,25,3000,25);
        int  time=0;
        int x=100;
        for(int i=100;i<3000;i=i+36){
            time=time+1;
            x=x+36;
            gc.strokeText(String.valueOf(time)+":00",x,40);
            if(time==23) time=-1;
        }
        int data=0;
        for(int i=100;i<3000;i=i+864){
            data=data+1;
            gc.strokeText(String.valueOf(data)+"日",i,15);

        }
        gc.strokeText("0:00",100,40);
        for(int i=100;i<1000;i=i+100){
            gc.strokeText("员工"+String.valueOf(i/100),50,i);
        }
        DrawTask(taskList,selList);


        HBox hbox1 = new HBox();//员工编号
        hbox1.setSpacing(20);
        Label l1=new Label("员工编号");
        final TextField id = new TextField();
        id.setPromptText("");
        id.setMaxWidth(50);
        hbox1.getChildren().addAll(l1,id);

        HBox hBox2=new HBox();//开始任务时间
        hbox1.setSpacing(20);
        Label l2=new Label("开始任务时间   ");
        final TextField beginTimeR = new TextField();
        beginTimeR.setPromptText("");
        beginTimeR.setMaxWidth(50);
        Label br=new Label("日");

        final TextField beginTimeS = new TextField();
        beginTimeS.setPromptText("");
        beginTimeS.setMaxWidth(50);
        Label bs=new Label("时");

        final TextField beginTimeF = new TextField();
        beginTimeF.setPromptText("");
        beginTimeF.setMaxWidth(50);
        Label bf=new Label("分");

        final TextField beginTimeM = new TextField();
        beginTimeM.setPromptText("");
        beginTimeM.setMaxWidth(50);
        Label bm=new Label("秒");
        hBox2.getChildren().addAll(l2,beginTimeR,br,beginTimeS,bs,beginTimeF,bf,beginTimeM,bm);

        HBox hBox3=new HBox();//结束任务时间
        hbox1.setSpacing(20);
        Label l3=new Label("结束任务时间   ");
        final TextField endTimeR = new TextField();
        endTimeR.setPromptText("");
        endTimeR.setMaxWidth(50);
        Label er=new Label("日");

        final TextField endTimeS = new TextField();
        endTimeS.setPromptText("");
        endTimeS.setMaxWidth(50);
        Label es=new Label("时");

        final TextField endTimeF = new TextField();
        endTimeF.setPromptText("");
        endTimeF.setMaxWidth(50);
        Label ef=new Label("分");

        final TextField endTimeM = new TextField();
        endTimeM.setPromptText("");
        endTimeM.setMaxWidth(50);
        Label em=new Label("秒");
        hBox3.getChildren().addAll(l3,endTimeR,er,endTimeS,es,endTimeF,ef,endTimeM,em);

        HBox hBox4=new HBox();
        Button addButton = new Button("提交任务");
        addButton.setOnAction(new EventHandler<ActionEvent>(){//加任务点击事件

            @Override
            public void handle(ActionEvent event) {
                //向tasklist插入数据
                if(id.getText()!=null) {
                    taskList.add(new Task(id.getText(), beginTimeR.getText() + "-" + beginTimeS.getText() + ":" + beginTimeF.getText() + ":" +
                            beginTimeM.getText(), endTimeR.getText() + "-" + endTimeS.getText() + ":" + endTimeF.getText() + ":" + endTimeM.getText()));

                    for(int i=0;i<taskList.size();i++){
                        System.out.println(taskList.get(i).beginData+"  "+taskList.get(i).endData+" "+taskList.get(i).id);
                    }
                    DrawTask(taskList,selList);
                }

                System.out.println(11111);
            }
        });

        hBox4.getChildren().add(addButton);


        HBox hbox5 = new HBox();//计算相对秒数相关
        hbox5.setSpacing(20);
        final TextField selTime1 = new TextField();
        Label lt=new Label("上一次位置时间");
        final TextField selTime2 = new TextField();
        Label nt=new Label("当前位置时间");
        Button JSButton = new Button("计算相对秒数");
        final TextField XDTime = new TextField();
        XDTime.setPromptText("相对秒数");
        hbox5.getChildren().addAll(lt,selTime1,nt,selTime2,JSButton,XDTime);

        JSButton.setOnAction(new EventHandler<ActionEvent>() {//计算秒数
            @Override

            public void handle(ActionEvent event) {
                XDTime.setText(String.valueOf(Math.abs(fnow-flast)));

            }
        });

        canvas.addEventHandler(MouseEvent.MOUSE_CLICKED,//显示当前时间
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent t) {


                        double time=t.getX();
                        double m=(time-100)/0.01;
                        if(fnow==0){
                            fnow=(int)m;
                        }
                        else {
                            flast=fnow;
                            fnow=(int)m;
                        }
                        int r=(int)m/86400+1;
                        m=m-86400*(r-1);
                        int s=(int)m/3600;
                        m=m-3600*s;
                        int f=(int)m/60;
                        m=m-60*f;
                        int v=(int)m;
                        String now=r+"日"+s+"时"+f+"分"+v+"秒";
                        if(time1==null){
                            time1=now;
                            time2=null;
                        }
                        else{
                            time2=time1;
                            time1=now;
                        }
                        selTime2.setText(time1);
                        selTime1.setText(time2);

                        //选中逻辑
                        double X=t.getX();
                        double Y=t.getY();
                        sel(X,Y);
                        //移动逻辑
                        if(selectX!=0&&selecty!=0){
                            boolean tip=false;
                            double nowY=t.getY();
                            int nowid=((int)nowY+30)/100;
                            for(int i=0;i<taskList.size();i++){
                                if(taskList.get(i).id.equals(String.valueOf(nowid))){
                                    if(selectX>change(taskList.get(i).beginData)&&selectX<change(taskList.get(i).endData)){
                                        tip=true;break;
                                    }
                                }
                            }
                            if(tip==false){
                                Task a=getTask(selectX,selecty);
                                taskList.remove(a);
                                taskList.add(new Task(String.valueOf(nowid),a.beginData,a.endData));
                                DrawTask(taskList,selList);
                            }

                        }
                        else {
                                selectX=t.getX();
                                selecty=t.getY();
                             }



                    }
                });


        ScrollPane sp = new ScrollPane();
        sp.setContent(canvas);
        sp.setHbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        sp.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        sp.setVmax(440);
        sp.setPrefSize(850, 350);

        VBox vBox=new VBox();
        vBox.setSpacing(20);
        vBox.getChildren().addAll(hbox1,hBox2,hBox3,hBox4,hbox5);
        vBox.setAlignment(Pos.BOTTOM_RIGHT);
        vBox.setLayoutY(400);

        root.getChildren().addAll(sp,vBox);

    }
    public Task getTask(double X,double Y){
        int id=((int) Y+30)/100;
        for(int i=0;i<taskList.size();i++){
            if(taskList.get(i).id.equals(String.valueOf(id))){
                if(X>change(taskList.get(i).beginData)&&X<change(taskList.get(i).endData)){
                    Task t=taskList.get(i);
                    return t;
                }
            }
        }
        return null;
    }

    public void sel(double x,double y){//判断当前任务是否添加到选中队列
        int id =(int) (y+30)/100;
        boolean tip=true;
        for(int i=0;i<selList.size();i++){
            if(String.valueOf(id).equals(selList.get(i).id)){
                if(x<change(selList.get(i).endData)&&x>change(selList.get(i).beginData)){
                    selList.remove(i);
                    tip=false;
                    DrawTask(taskList,selList);
                }
            }
        }
        if(tip) {
            for (int i = 0; i < taskList.size(); i++) {
                if (String.valueOf(id).equals(taskList.get(i).id)) {
                    if (x < change(taskList.get(i).endData) && x > change(taskList.get(i).beginData)) {
                        selList.add(taskList.get(i));
                        DrawTask(taskList, selList);
                    }
                }
            }

        }
    }

    public  double change(String time){//task的Time换算成坐标
        double xr1=(Integer.parseInt(time.substring(0,2))-1)*864;
        double xs1=Integer.parseInt(time.substring(3,5))*36;
        double xf1=Integer.parseInt(time.substring(6,7))*0.6;
        double xm1=Integer.parseInt(time.substring(9,11))*0.01;
        double X=xr1+xf1+xs1+xm1+100;
        return X;
    }

    public void initData(){//初始化数据
        taskList.add(new Task("1","01-12:15:30","01-15:12:00"));
        taskList.add(new Task("1","02-04:25:30","02-08:24:10"));
        taskList.add(new Task("2","03-20:30:30","03-22:10:20"));
        taskList.add(new Task("3","01-06:22:30","02-03:10:20"));
        taskList.add(new Task("4","01-07:44:30","01-15:35:00"));
        taskList.add(new Task("5","02-22:00:30","02-23:10:20"));
        taskList.add(new Task("6","02-15:45:30","02-17:10:50"));
        taskList.add(new Task("7","03-17:40:30","03-20:10:40"));
        taskList.add(new Task("2","03-13:20:30","03-15:10:20"));
        taskList.add(new Task("3","03-10:10:30","03-15:10:20"));
    }

    private void DrawTask(LinkedList<Task> dataList,LinkedList<Task> selList){//根据datalist去重画画布

        for(int i=0;i<dataList.size();i++){//遍历每个数据去画任务
            Task t=dataList.get(i);
            String beginData=t.beginData;
            String endData=t.endData;
            String id=t.id;
            int y = Integer.parseInt(id)*100;//得到Y坐标
            //得到起点坐标的X坐标
            double xr1=(Integer.parseInt(beginData.substring(0,2))-1)*864;
            double xs1=Integer.parseInt(beginData.substring(3,5))*36;
            double xf1=Integer.parseInt(beginData.substring(6,7))*0.6;
            double xm1=Integer.parseInt(beginData.substring(9,11))*0.01;
            double x1=xr1+xf1+xs1+xm1+100;

            //得到终点坐标的X坐标
            double xr2=(Integer.parseInt(endData.substring(0,2))-1)*864;
            double xs2=Integer.parseInt(endData.substring(3,5))*36;
            double xf2=Integer.parseInt(endData.substring(6,7))*0.6;
            double xm2=Integer.parseInt(endData.substring(9,11))*0.01;
            double x2=xr2+xf2+xs2+xm2+100;
            gc.setLineWidth(30);
            gc.setStroke(Color.GREEN);
            gc.strokeLine(x1,y,x2,y);
        }

        for(int i=0;i<selList.size();i++){//遍历选中列表画边框
            Task s=selList.get(i);
            String beginData=s.beginData;
            String endData=s.endData;
            String id=s.id;
            int y = Integer.parseInt(id)*100;//得到Y坐标
            //得到起点坐标的X坐标
            double xr1=(Integer.parseInt(beginData.substring(0,2))-1)*864;
            double xs1=Integer.parseInt(beginData.substring(3,5))*36;
            double xf1=Integer.parseInt(beginData.substring(6,7))*0.6;
            double xm1=Integer.parseInt(beginData.substring(9,11))*0.01;
            double x1=xr1+xf1+xs1+xm1+100;

            //得到终点坐标的X坐标
            double xr2=(Integer.parseInt(endData.substring(0,2))-1)*864;
            double xs2=Integer.parseInt(endData.substring(3,5))*36;
            double xf2=Integer.parseInt(endData.substring(6,7))*0.6;
            double xm2=Integer.parseInt(endData.substring(9,11))*0.01;
            double x2=xr2+xf2+xs2+xm2+100;
            gc.setLineWidth(30);
            gc.setStroke(Color.RED);
            gc.strokeLine(x1,y,x2,y);
        }

    }

    public static void main(String[] args) {
        launch(args);
    }
}
