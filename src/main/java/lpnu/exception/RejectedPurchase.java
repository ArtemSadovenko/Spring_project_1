package lpnu.exception;

public class RejectedPurchase extends RuntimeException{
    private int code;
    private String massage;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMassage() {
        return massage;
    }

    public void setMassage(String massage) {
        this.massage = massage;
    }

    public RejectedPurchase(String massage){
        this.massage = massage;
        this.code = 400;
    }

    public RejectedPurchase(String massage, int code){
        this.massage = massage;
        this.code = code;
    }
}
