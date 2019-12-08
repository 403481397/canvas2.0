package sample;



public class Task {
    public String id;
    public String beginData;
    public String endData;

    public Task(){

    }


    public Task(String id, String beginData, String endData){
        this.id=id;
        this.beginData=beginData;
        this.endData=endData;

    }

    public String getBeginData() {
        return beginData;
    }

    public void setBeginData(String beginData) {
        this.beginData = beginData;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEndData() {
        return endData;
    }

    public void setEndData(String endData) {
        this.endData = endData;
    }
}
