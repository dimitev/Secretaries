package k3;

import java.io.Serializable;
import java.util.Objects;

public class Document implements Serializable
{
    private String name;
    private Double score;
    private Double income;
    private boolean special;
    private int gotSpecial;
    private int gotSuccess;
    public Document(String name,Double score,Double income,boolean special)
    {
        this.name=name;
        this.score=score;
        this.income=income;
        this.special=special;
        this.gotSpecial=0;
        this.gotSuccess=0;
    }
    public void print()
    {
        System.out.println(this.name+" "+this.score+" "+this.income+"lv"+" cand. for special-"+special);
    }
    public boolean isSpecial()
    {
        return special;
    }
    public void incSpecial()
    {
        this.gotSpecial++;
    }
    public void incSuccess()
    {
        this.gotSuccess++;
    }
    public int getSpecial()
    {
        return gotSpecial;
    }
    public int getSucces()
    {
        return gotSuccess;
    }
    public double getScore()
    {
        return score;
    }
    public double getIncome()
    {
        return this.income;
    }
    @Override
    public int hashCode()
    {
            int hash=150;
            hash=hash+this.name.hashCode()+this.score.hashCode()+this.income.hashCode();
            return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Document other = (Document) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.score, other.score)) {
            return false;
        }
        if (!Objects.equals(this.income, other.income)) {
            return false;
        }
        return true;
    }
}
