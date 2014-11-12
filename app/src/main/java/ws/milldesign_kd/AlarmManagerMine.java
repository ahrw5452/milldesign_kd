package ws.milldesign_kd;

import java.util.Calendar;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/*このクラスの司る処理
* ユーザがセットした時間に処理を発動させる
* (PendingIntentを設定する)
* 必要な情報をサービスクラスに渡す
*/

public class AlarmManagerMine {
    Context context;
    AlarmManager alarmManager;
    private PendingIntent mAlarmSender;

    public AlarmManagerMine(Context context){
        this.context = context;
        alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
    }

    //繰り返し有りでアラームをセットする場合
    public void RepertAddAlarm(long alarmSetTime){


        String uniqueParam = Long.toString(alarmSetTime) ;
        Intent intent = new Intent(context, AlarmServiceMine.class);

        //ユニークなインテントであることを示す(これをしないとセットする度アラームが上書きされてしまう)
        intent.setType(uniqueParam);

        mAlarmSender = PendingIntent.getService(context, -1, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        alarmManager.set(AlarmManager.RTC_WAKEUP,alarmSetTime,mAlarmSender);
    }

    //繰り返し無しでアラームをセットする場合
    public void noRepertAddAlarm(long alarmSetTime){

        String uniqueParam = Long.toString(alarmSetTime) ;
        Intent intent = new Intent(context, AlarmServiceMine.class);

        //ユニークなインテントであることを示す(これをしないとセットする度アラームが上書きされてしまう)
        intent.setType(uniqueParam);

        mAlarmSender = PendingIntent.getService(context, -1, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        /*
        * アラームマネージャのset機能を解説。
        * まずこのsetは、任意の時間で実行する処理を登録
        * 第一引数　第二引数の指定のタイプを選択。RTCを指定するとUTC時刻での指定となる。_WAKEUPはオプションで、指定した時間が来たらアプリがスリープ状態でも電源をONしてくれる。
        *           ※UTC時刻とは全世界で時刻を記録する際に使われる公式な時刻。日本との時差は約マイナス9時間。(日本は世界の公式時刻より9時間早く生活している、日本が午後15時なら公式時刻は朝6時)
        * 第二引数　処理を実行する任意の時間。今回はUTC時刻での指定。
        * 第三引数　PendingIntent operationとやら。
        *           Intentを指定したタイミングで発行するのがPendingIntent。
        *           では、PendingIntentのgetServiceを見ていく。
        *           まず第一引数。これは定型でcontextを入れる。
        *           次に第二引数。これも定型。-1。
        *           次に第三引数。これは発行されるIntent。ここではMyAlarmServiceのインスタンスを作って入れてる。
        *           そして第四引数。ここにはフラグを入れる。フラグの種類は下記。
        *               FLAG_CANCEL_CURRENT：過去の設定をキャンセルし現在の設定を使う
        *               FLAG_NO_CREATE：存在してなければNullを返す。新規には作成しない。
        *               FLAG_ONE_SHOT：一度だけこのIntentを使用できる。
        *               FLAG_UPDATE_CURRENT：過去の設定があればそれを使う。再設定しない。
        *           第四引数は上書きの設定のようなので。
        *           今回のように複数のアラームをセットしたい時には気を付けて設定する。
        */
        alarmManager.set(AlarmManager.RTC_WAKEUP,alarmSetTime,mAlarmSender);
    }
}