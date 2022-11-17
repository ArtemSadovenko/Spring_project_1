package lpnu.exception;

public class IrregularDateDTO {
    private int code;
    private String massage;

    public IrregularDateDTO(String massage, int code)
    {
        this.massage = massage;
        this.code = code;
    }
    public IrregularDateDTO(String massage)
    {
        this.massage = massage;
        this.code = 400;
    }

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
}
