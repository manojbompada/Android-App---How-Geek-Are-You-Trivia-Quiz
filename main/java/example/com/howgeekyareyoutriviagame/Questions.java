/*
    Cole Howell, Manoj Bompada, Justin Le
    Questions.java
    ITCS 4180
 */

package example.com.howgeekyareyoutriviagame;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by colehowell on 2/16/16.
 */
public class Questions implements Parcelable{
    String qid,questtxt,opttxt1,opttxt1score,opttxt2,opttxt2score,opttxt3,opttxt3score,opttxt4,opttxt4score,opttxt5,opttxt5score,questimgUrl;

    public Questions(String qid, String questtxt, String opttxt1, String opttxt1score, String opttxt2, String opttxt2score,
                     String opttxt3, String opttxt3score, String opttxt4, String opttxt4score, String opttxt5, String opttxt5score,
                     String questimgUrl)
    {
        this.qid = qid;
        this.questtxt = questtxt;
        this.opttxt1 = opttxt1;
        this.opttxt1score = opttxt1score;
        this.opttxt2 = opttxt2;
        this.opttxt2score = opttxt2score;
        this.opttxt3 = opttxt3;
        this.opttxt3score = opttxt3score;
        this.opttxt4 = opttxt4;
        this.opttxt4score = opttxt4score;
        this.opttxt5 = opttxt5;
        this.opttxt5score = opttxt5score;
        this.questimgUrl = questimgUrl;
    }

    private Questions(Parcel in) {
        qid = in.readString();
        questtxt = in.readString();
        opttxt1 = in.readString();
        opttxt1score = in.readString();
        opttxt2 = in.readString();
        opttxt2score = in.readString();
        opttxt3 = in.readString();
        opttxt3score = in.readString();
        opttxt4 = in.readString();
        opttxt4score = in.readString();
        opttxt5 = in.readString();
        opttxt5score = in.readString();
        questimgUrl = in.readString();
    }

    public static final Creator<Questions> CREATOR = new Creator<Questions>() {
        @Override
        public Questions createFromParcel(Parcel in)
        {
            return new Questions(in);
        }

        @Override
        public Questions[] newArray(int size)
        {
            return new Questions[size];
        }
    };

    @Override
    public int describeContents()
    {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeString(qid);
        dest.writeString(questtxt);
        dest.writeString(opttxt1);
        dest.writeString(opttxt1score);
        dest.writeString(opttxt2);
        dest.writeString(opttxt2score);
        dest.writeString(opttxt3);
        dest.writeString(opttxt3score);
        dest.writeString(opttxt4);
        dest.writeString(opttxt4score);
        dest.writeString(opttxt5);
        dest.writeString(opttxt5score);
        dest.writeString(questimgUrl);

    }
}
