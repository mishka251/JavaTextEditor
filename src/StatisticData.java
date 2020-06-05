import java.util.Date;

public class StatisticData {
    private int soglasn;
    private int glasn;
    private Date saveTime;
    private int filledFields;
    private String fullText;

    public StatisticData(int sogl, int glas, Date saveTime, int filledFields, String fullText) {
        soglasn = sogl;
        glasn = glas;
        this.saveTime = saveTime;
        this.filledFields = filledFields;
        this.fullText = fullText;
    }

    public int getGlasn() {
        return glasn;
    }

    public int getSoglasn() {
        return soglasn;
    }

    public Date getSaveTime() {
        return saveTime;
    }

    public int getFilledFields() {
        return filledFields;
    }

    public String getFullText() {
        return fullText;
    }
}
