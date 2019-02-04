package secured.tips;

/**
 * Created by Mendhie on 2/1/2018.
 */

public class TipDetails {

    private String Hth;
    private String League;
    private String Matches;
    private Double Odd;
    private Long Result;
    private String Tip;

    public TipDetails(){}


    public String getHth() {
        return Hth;
    }
    public void setHth(String Hth) {
        this.Hth = Hth;
    }

    public String getLeague(){return League;}
    public void setLeague(String League){
        this.League = League;
    }

    public String getMatches(){return Matches;}
    public void setMatches(String Matches){
        this.Matches = Matches;
    }

    public String getTip(){return Tip;}
    public void setTips(String Tip){
        this.Tip = Tip;
    }

    public Double getOdd(){return Odd;}
    public void setOdd(Double Odd){
        this.Odd = Odd;
    }

    public Long getResult(){return Result;}{}
    public void setResult(Long Result){
        this.Result = Result;
    }
}