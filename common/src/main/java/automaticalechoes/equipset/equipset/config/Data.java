package automaticalechoes.equipset.equipset.config;

public class Data<T>{
    T data;

    public Data(T t){
        data = t;
    }


    public void setData(T data) {
        this.data = data;
    }

    public T getData() {
        return data;
    }
}
