package com.example.seo.festivalsendmessages.DbHelpers;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Seo on 2016/3/8.
 */
public class DbOpenHelper extends SQLiteOpenHelper {
                                       //1     2       3      4         5        6       7          8           9         10      11        12    13       14
    private String[] festival_Names = {"元旦","除夕","春节","元宵节","情人节","劳动节","母亲节","520(我爱你)","端午节","父亲节","七夕","中秋节","国庆节","圣诞节"};
    private String[] festival_Dates = {"1-1","2-8",   "2-8","2-11",   "2-14",   "5-1",  "5-14" ,  "5-20",      "5-30", "6-18","8-9", "9-15","10-1","12-25"};
    public static final String FestivalDatesTable = "FestivalDates";
    public static final String FestivalMessagesTable = "FestivalMessages";
    public static final String MessageHistroyTable = "MessageHistroy";
    public static final String RegularlySendTable = "RegularlySend";
    public static final String[] FestivalDatesTableColumns = {"festivalName","festivalDate"};
    public static final String[] FestivalMessagesTableColumns = {"festivalId","messageText"};
    public static final String[] MessageHistroyTableColumns = {"acceptanceNumber","messageId","festivalId","sendDate"};
    public static final String[] RegularlySendTableColumns = {"regularlyDate","messageText","acceptPeople","isSend"};

    public DbOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public DbOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table if not exists "+FestivalDatesTable+
                "(" +
                "_id integer primary key autoincrement," +
                FestivalDatesTableColumns[0]+" text not null," +
                FestivalDatesTableColumns[1]+" text not null)");
        db.execSQL("create table if not exists "+FestivalMessagesTable+
                "(" +
                "_id integer primary key autoincrement," +
                ""+FestivalMessagesTableColumns[0]+" integer not null," +
                ""+FestivalMessagesTableColumns[1]+" text not null," +
                "foreign key(festivalId) references FestivalDates(_id))");
        db.execSQL("create table if not exists "+MessageHistroyTable+
                "(" +
                "_id integer primary key autoincrement," +
                ""+MessageHistroyTableColumns[0]+" text not null," +
                ""+MessageHistroyTableColumns[1]+" integer not null," +
                ""+MessageHistroyTableColumns[2]+" integer not null," +
                ""+MessageHistroyTableColumns[3]+" text not null,"+
                "foreign key(festivalId) references FestivalDates(_id)," +
                "foreign key(messageId) references FestivalMessages(_id))"); //设置外键
        db.execSQL("create table if not exists "+RegularlySendTable+
                "(" +
                "_id integer primary key autoincrement," +
        ""+RegularlySendTableColumns[0]+"text not null,"+
        ""+RegularlySendTableColumns[1]+"text not null,"+
        ""+RegularlySendTableColumns[2]+"text not null,"+
        ""+RegularlySendTableColumns[3]+"NUMERIC not null" +
                ")");
        for(int i = 0;i<festival_Names.length;i++)
        {
            db.execSQL("insert into FestivalDates(festivalName,festivalDate) values('"+festival_Names[i]+"','"+festival_Dates[i]+"')");
        }
        db.execSQL("insert into "+FestivalMessagesTable+"("+FestivalMessagesTableColumns[0]+","+FestivalMessagesTableColumns[1]+") values(1,'元旦了，我送了一串礼物。快乐送给开心的人，幸福送给有情的人，希望送给等待的人，成功送给奋斗的人，祝福送给正看短信的人')");
        db.execSQL("insert into "+FestivalMessagesTable+"("+FestivalMessagesTableColumns[0]+","+FestivalMessagesTableColumns[1]+") values(1,'一斤花生二斤枣，愿你跟着好运跑；三斤橘子四斤蕉，财源滚进你腰包；五斤葡萄六斤橙，愿你心想事就成；八斤芒果十斤瓜，身体健康顶刮刮！祝你：元旦快乐！')");
        db.execSQL("insert into "+FestivalMessagesTable+"("+FestivalMessagesTableColumns[0]+","+FestivalMessagesTableColumns[1]+") values(1,'元旦到，许心愿：愿你事业高升金钱花不完，万事如意一生永平安。爱神也会把你恋，生活美满心也甜；喜神陪伴你身边，祝你天天展笑颜！')");
        db.execSQL("insert into "+FestivalMessagesTable+"("+FestivalMessagesTableColumns[0]+","+FestivalMessagesTableColumns[1]+") values(1,'房子，车子，票子，不如开心过日子；微信，彩信，飞信，不如问候小短信；金蛋，银蛋，彩蛋，不如快乐过元旦。愿你龙年元旦，幸福翻天。')");

        db.execSQL("insert into "+FestivalMessagesTable+"("+FestivalMessagesTableColumns[0]+","+FestivalMessagesTableColumns[1]+") values(2,'过年好，祝您：身体健康，万事如意，合家欢乐，生活美满，事业有成，珠玉满堂，多寿多富，财大气粗，攻无不克，战无不胜！')");
        db.execSQL("insert into "+FestivalMessagesTable+"("+FestivalMessagesTableColumns[0]+","+FestivalMessagesTableColumns[1]+") values(2,'此信虽无墨香，笔尖祈愿徜徉，举目间有祝福流淌；幸福新年时光，心中情谊激荡，愿您一生无忧，快乐美满，幸福常伴，春节欢畅！')");
        db.execSQL("insert into "+FestivalMessagesTable+"("+FestivalMessagesTableColumns[0]+","+FestivalMessagesTableColumns[1]+") values(2,'春节临近，愿你抱着平安，拥着健康，揣着幸福，携着快乐，搂着温馨，带着甜蜜，牵着财运，拽着吉祥，迈入新年快乐度过每一天！')");
        db.execSQL("insert into "+FestivalMessagesTable+"("+FestivalMessagesTableColumns[0]+","+FestivalMessagesTableColumns[1]+") values(2,'初春雪漫漫，人间处处新春节快乐，新年幸福辞旧迎新，心想事成以真挚热诚的祝福，在春节之际表示思念之情祝春节快乐，新年快乐！')");

        db.execSQL("insert into "+FestivalMessagesTable+"("+FestivalMessagesTableColumns[0]+","+FestivalMessagesTableColumns[1]+") values(3,'无钱不恼，有钱不骄，生活不易，知足最好，快乐多多，烦恼少少，健康常伴，平安笼罩，友情拥抱，亲情围 绕，紫气东来，福运缭绕，新春没到，祝福早到。2017新年快乐愉快!')");
        db.execSQL("insert into "+FestivalMessagesTable+"("+FestivalMessagesTableColumns[0]+","+FestivalMessagesTableColumns[1]+") values(3,'新年到，祝福来报到：大财小财意外财，财源滚滚;亲情爱情朋友情，份份真情;官运财运桃花运，运运亨通;爱人亲人家里人，人人平安。')");
        db.execSQL("insert into "+FestivalMessagesTable+"("+FestivalMessagesTableColumns[0]+","+FestivalMessagesTableColumns[1]+") values(3,'车如梭，人如潮，一年春运如期到。千山横，万水绕，回家之行路迢迢。报平安，佳讯捎，阖家团圆乐陶陶。祝归乡之路一路顺风!')");
        db.execSQL("insert into "+FestivalMessagesTable+"("+FestivalMessagesTableColumns[0]+","+FestivalMessagesTableColumns[1]+") values(3,'寒又逢年末，短信捎上问候：保持注意防寒，手套围巾戴好;心情保持舒畅，莫要心烦意燥;关怀莫要忘掉，空时找我唠叨。祝你大寒暖暖，年末逍遥!')");

        db.execSQL("insert into "+FestivalMessagesTable+"("+FestivalMessagesTableColumns[0]+","+FestivalMessagesTableColumns[1]+") values(4,'正月十五月儿圆，合家团聚在桌前。端上汤圆尝一碗，甜甜蜜蜜心头暖。出门再去花灯观，其乐融融人人羡。声声祝福不间断，快快乐乐笑开颜。祝元宵节快乐无限！')");
        db.execSQL("insert into "+FestivalMessagesTable+"("+FestivalMessagesTableColumns[0]+","+FestivalMessagesTableColumns[1]+") values(4,'你真是好福气，幸福的日子接二连三地到来，刚刚过完春节又迎来元宵节，为了防止信息堵塞，我的祝福提前出发，祝你元宵节快乐，吉祥、如意、幸福！')");
        db.execSQL("insert into "+FestivalMessagesTable+"("+FestivalMessagesTableColumns[0]+","+FestivalMessagesTableColumns[1]+") values(4,'元宵节的祝福最甜，元宵节的问候最润，元宵节的团圆最有滋味，月亮圆，圆的开心，元宵圆，圆的幸福，人团圆，圆的美满，朋友祝你元宵节快乐。')");
        db.execSQL("insert into "+FestivalMessagesTable+"("+FestivalMessagesTableColumns[0]+","+FestivalMessagesTableColumns[1]+") values(4,'正月十五元宵吃汤圆：你圆、我圆，人人结缘！日圆、月圆，事事皆圆！人源、福源，处处皆源！官源、财源，源源不断！心愿、情愿，愿愿称心！')");

        db.execSQL("insert into "+FestivalMessagesTable+"("+FestivalMessagesTableColumns[0]+","+FestivalMessagesTableColumns[1]+") values(5,'每天都幻想着和你背靠着背的一起喝奶茶，丝丝茶香围绕着我们。每天都给你发去我无限的祝福，只因为你一直在我心里。宝贝，情人节快乐！')");
        db.execSQL("insert into "+FestivalMessagesTable+"("+FestivalMessagesTableColumns[0]+","+FestivalMessagesTableColumns[1]+") values(5,'花儿红艳怕风霜，老婆憔悴我伤感。只因平日太辛苦，没能照管把你伤。子女都有工作干，你却累倒重病患。今日情人节来到，惟愿老婆身体康！')");
        db.execSQL("insert into "+FestivalMessagesTable+"("+FestivalMessagesTableColumns[0]+","+FestivalMessagesTableColumns[1]+") values(5,'情人节到了，要切记提高情人的幸福指数，送玫瑰，早约会，逛遍大街小巷莫嫌累，腰包弹尽粮绝别怕贵，要的就是个心情美，哪怕过后喝白水。')");
        db.execSQL("insert into "+FestivalMessagesTable+"("+FestivalMessagesTableColumns[0]+","+FestivalMessagesTableColumns[1]+") values(5,'春来百花绽芬芳，你我情意暖心肠。同来同往同希望，呵护幸福万年长。同心同德同梦想，播下真爱伴花香。情人佳节到身旁，只愿爱人心欢畅。')");

        db.execSQL("insert into "+FestivalMessagesTable+"("+FestivalMessagesTableColumns[0]+","+FestivalMessagesTableColumns[1]+") values(6,'您用勤劳的双手，创造了夸姣糊口，平时工作忙碌碌，趁着五一狂购物。两手不空满载归，慰劳自己不能误。祝劳动节痛快！')");
        db.execSQL("insert into "+FestivalMessagesTable+"("+FestivalMessagesTableColumns[0]+","+FestivalMessagesTableColumns[1]+") values(6,'送父亲一件外套，前面是平安，后面是幸福，吉祥是领子，如意是袖子，快乐是扣子，让它伴父亲每一天，祝五一节日快乐。')");
        db.execSQL("insert into "+FestivalMessagesTable+"("+FestivalMessagesTableColumns[0]+","+FestivalMessagesTableColumns[1]+") values(6,'地球人都放假了，唯有我这条短信在加班。它满载祝福，跨过重重高山、越过滔滔江水飞到你身边：五一快乐，事事顺心哦！')");
        db.execSQL("insert into "+FestivalMessagesTable+"("+FestivalMessagesTableColumns[0]+","+FestivalMessagesTableColumns[1]+") values(6,'五一来到发短信，捎去思念和祝愿，温情拨动你心弦，问候带去保平安。五一劳动节，愿快乐与你永相伴，好运常在你身边。')");

        db.execSQL("insert into "+FestivalMessagesTable+"("+FestivalMessagesTableColumns[0]+","+FestivalMessagesTableColumns[1]+") values(7,'生命如此奇妙，感觉如此美妙。血脉共呼共吸，两人相依相靠。为你真心欢喜，祝福及时赶到。亲爱的妈妈，祝您节日快乐！')");
        db.execSQL("insert into "+FestivalMessagesTable+"("+FestivalMessagesTableColumns[0]+","+FestivalMessagesTableColumns[1]+") values(7,'无论你走了多远，无论你成功或是失败，在母亲的眼中，你永远是她最爱的孩子。在母亲的节日时，给妈妈一声问候一份祝福。')");
        db.execSQL("insert into "+FestivalMessagesTable+"("+FestivalMessagesTableColumns[0]+","+FestivalMessagesTableColumns[1]+") values(7,'开心的事儿和她聊一聊，骄傲的事儿向她耀一耀，成功的事儿向她报一报，琐碎的事儿向她唠一唠，母亲节，别忘了将她抱一抱。')");
        db.execSQL("insert into "+FestivalMessagesTable+"("+FestivalMessagesTableColumns[0]+","+FestivalMessagesTableColumns[1]+") values(7,'母亲节到了，我也没啥好送的，送您五万万吧：万万要开心，万万要舒心，万万要健康，万万要长寿还有，万万不要担心我。节日快乐。')");

        db.execSQL("insert into "+FestivalMessagesTable+"("+FestivalMessagesTableColumns[0]+","+FestivalMessagesTableColumns[1]+") values(8,'暖暖的关怀，倍感亲切;浓浓的爱意，萦绕心头;深深的眷恋，情浓爱厚;痴痴的相守，永不言弃;涓涓的真情，尽情流淌;真真的誓言，响彻云霄：520我爱你，爱你爱到心坎里!')");
        db.execSQL("insert into "+FestivalMessagesTable+"("+FestivalMessagesTableColumns[0]+","+FestivalMessagesTableColumns[1]+") values(8,'花谢了，我让云装饰你的梦;天黑了，我让萤火虫点亮你的心;叶落了，我让春天挤走你的失落;谢谢你让我出现在你的生命里，520表白日，爱你，永续!')");
        db.execSQL("insert into "+FestivalMessagesTable+"("+FestivalMessagesTableColumns[0]+","+FestivalMessagesTableColumns[1]+") values(8,'世界上，最浪漫的力量，是不离不弃;最温柔的力量，是一心一意;最强大的力量，是无怨无悔;最快乐的力量，是一生一世。520我爱你，世界上最幸福的力量，是爱你到老。')");
        db.execSQL("insert into "+FestivalMessagesTable+"("+FestivalMessagesTableColumns[0]+","+FestivalMessagesTableColumns[1]+") values(8,'爱你一生一世是我的誓言，铿锵有力永远不会变，咱们彼此呵护互相体贴，我给你理解你给我温存，亲爱的爱人是你给了我希望给了我力量，我要对你说一句我要爱你直到海枯石烂。')");

        db.execSQL("insert into "+FestivalMessagesTable+"("+FestivalMessagesTableColumns[0]+","+FestivalMessagesTableColumns[1]+") values(9,'我绝望了，紫霞离开了我，师傅太唠叨，牛魔王欠的钱又不还，好不容易买下水帘洞又被菩萨恶意收购，只剩下一毛钱发条短信祝二师弟端午快乐！')");
        db.execSQL("insert into "+FestivalMessagesTable+"("+FestivalMessagesTableColumns[0]+","+FestivalMessagesTableColumns[1]+") values(9,'香甜的糯米，粘住的全是真情，蜜蜜的红枣，散发的满是真诚，芬芳的粽叶，包裹的全是祝福，端午节，将这个粽子送给你，好好把它存在手机里，会给你带来好运无限，幸福无数，端午节快乐！')");
        db.execSQL("insert into "+FestivalMessagesTable+"("+FestivalMessagesTableColumns[0]+","+FestivalMessagesTableColumns[1]+") values(9,'今天是端午节！我收集世上一切美好的东西为原料，用无忧为外衣，以我的真心祝福为丝带，为你包了一个特别的粽子！吃了它你将永远快乐幸福！')");
        db.execSQL("insert into "+FestivalMessagesTable+"("+FestivalMessagesTableColumns[0]+","+FestivalMessagesTableColumns[1]+") values(9,'今天是端午，送你只香甜粽子：以芬芳的祝福为叶，以宽厚的包容为米，以温柔的叮咛做馅，再用友情的丝线缠绕，愿你品尝出人生的美好和这五月五的情怀！')");

        db.execSQL("insert into "+FestivalMessagesTable+"("+FestivalMessagesTableColumns[0]+","+FestivalMessagesTableColumns[1]+") values(10,'父亲的教诲像‘一盏明灯’，照亮我前程;父亲的关怀像‘一把雨伞’，为我遮风挡雨。父亲节到了，祝亲爱的父亲节日快乐!一生平安!')");
        db.execSQL("insert into "+FestivalMessagesTable+"("+FestivalMessagesTableColumns[0]+","+FestivalMessagesTableColumns[1]+") values(10,'比地宽广的是海，比海高远的是天，比天更高远的是父亲的胸怀;父亲节到了，祝天下所有父亲：开开心心快快乐乐，健健康康平平安安!')");
        db.execSQL("insert into "+FestivalMessagesTable+"("+FestivalMessagesTableColumns[0]+","+FestivalMessagesTableColumns[1]+") values(10,'父亲给了我‘一片蓝天’;父亲给我了‘一方沃土’;父亲是我生命里的‘永恒太阳’;父亲是我永远的‘精神支柱’，父亲节，祝父亲永远健康快乐!')");
        db.execSQL("insert into "+FestivalMessagesTable+"("+FestivalMessagesTableColumns[0]+","+FestivalMessagesTableColumns[1]+") values(10,'让阳光晒晒，忧愁溜走;让清风吹吹，疲惫不留;让小溪哗哗，天天有钱花;让月亮闪闪，暖暖问候。父亲节到了，祝您节日快乐，幸福长久!')");

        db.execSQL("insert into "+FestivalMessagesTable+"("+FestivalMessagesTableColumns[0]+","+FestivalMessagesTableColumns[1]+") values(11,'希望能牵手一起走过今后的日子，不管是鲜花铺路，还是荆棘满地，不离不弃，彼此相爱，我会永远伴随你左右！祝你七夕节快乐！')");
        db.execSQL("insert into "+FestivalMessagesTable+"("+FestivalMessagesTableColumns[0]+","+FestivalMessagesTableColumns[1]+") values(11,'在一起的日子很平淡，似乎波澜不惊，只是，这种平凡的日子是最浪漫的，对吗？亲爱的，七夕情人节快乐！')");
        db.execSQL("insert into "+FestivalMessagesTable+"("+FestivalMessagesTableColumns[0]+","+FestivalMessagesTableColumns[1]+") values(11,'爱上你是我今生最大的幸福，想你是我最甜蜜的痛苦，和你在一起是我的骄傲，没有你的我就像一只迷失了航线的船。让我们相爱到老，我爱你！')");
        db.execSQL("insert into "+FestivalMessagesTable+"("+FestivalMessagesTableColumns[0]+","+FestivalMessagesTableColumns[1]+") values(11,'夏天你帮我扇风，冬天你帮我暖被窝，那个人永远是你，我的老公，七夕了，让我们一起共度中国的情人节。')");

        db.execSQL("insert into "+FestivalMessagesTable+"("+FestivalMessagesTableColumns[0]+","+FestivalMessagesTableColumns[1]+") values(12,'后羿忙射日，嫦娥偷奔月；月老不曾老，自古情难了；中秋月儿挂，心中圆往事；玉兔伴嫦娥，绢带千古相思！')");
        db.execSQL("insert into "+FestivalMessagesTable+"("+FestivalMessagesTableColumns[0]+","+FestivalMessagesTableColumns[1]+") values(12,'心到想到得到，看到闻到吃到，福到运到财到，八月十五还没到，但愿我的短信是第一个到，提前祝你八月十五快乐！')");
        db.execSQL("insert into "+FestivalMessagesTable+"("+FestivalMessagesTableColumns[0]+","+FestivalMessagesTableColumns[1]+") values(12,'合作的天总是阳光灿烂，私下的交情总是共苦同甘；今借中秋将至，我祝愿你，我的客户好友，中秋收获满满，顺利如愿！')");
        db.execSQL("insert into "+FestivalMessagesTable+"("+FestivalMessagesTableColumns[0]+","+FestivalMessagesTableColumns[1]+") values(12,'明月皆无价，朋友皆有情。路遥千里，难继相思。人虽难至，心相融之。中秋节到了，衷心祝愿您和家人团圆美满，幸福安康！')");

        db.execSQL("insert into "+FestivalMessagesTable+"("+FestivalMessagesTableColumns[0]+","+FestivalMessagesTableColumns[1]+") values(13,'愿我的祝福是小河，悄悄流淌在你身边不被你发现，愿我的祝福是云朵，时刻在注视着你，却总是默默的，国庆节，祝愿你节日快乐。')");
        db.execSQL("insert into "+FestivalMessagesTable+"("+FestivalMessagesTableColumns[0]+","+FestivalMessagesTableColumns[1]+") values(13,'乞丐带着猴子沿街乞讨，他叫猴子笑它就笑，叫猴子哭它就哭，叫猴子作揖它就作揖，叫猴子看短信它就看短信。呵呵，国庆节快乐！')");
        db.execSQL("insert into "+FestivalMessagesTable+"("+FestivalMessagesTableColumns[0]+","+FestivalMessagesTableColumns[1]+") values(13,'秋天会带走夏的美丽，却带不走我对你的心意，尽管我们匆匆的来去，却要在假日里送给你一份甜蜜，愿你在美好的日子里一切如意。')");
        db.execSQL("insert into "+FestivalMessagesTable+"("+FestivalMessagesTableColumns[0]+","+FestivalMessagesTableColumns[1]+") values(13,'十月一，国庆节，阳光明媚红旗飘扬，金秋季，花儿红，人民的生活比蜜糖，向阳花，金灿灿，祖国生日普天同庆，祝你明天越来越好。')");

        db.execSQL("insert into "+FestivalMessagesTable+"("+FestivalMessagesTableColumns[0]+","+FestivalMessagesTableColumns[1]+") values(14,'你好!我是圣诞老人。很抱歉的告诉你，由于在过去的一年，我嘴巴馋、身体懒，吃了睡，睡了吃，因此已经发福，钻不进烟囱啦，只能口头祝你圣诞快乐!')");
        db.execSQL("insert into "+FestivalMessagesTable+"("+FestivalMessagesTableColumns[0]+","+FestivalMessagesTableColumns[1]+") values(14,'话藏在心底难说出口，热闹的街道不再冷清，寒冬的冷冽在不知觉间淡忘，心中盼望着圣诞来临的惊喜。我想和你说一句：圣诞陪我过，记得带钱包!')");
        db.execSQL("insert into "+FestivalMessagesTable+"("+FestivalMessagesTableColumns[0]+","+FestivalMessagesTableColumns[1]+") values(14,'圣诞树已装点完毕，窗外的风雪依旧不停;浪漫晚餐已准备就绪，午夜的钟声即将响起。圣诞即将来临，我想轻轻传我心意：祝你圣诞快乐，礼物拿来!')");
        db.execSQL("insert into "+FestivalMessagesTable+"("+FestivalMessagesTableColumns[0]+","+FestivalMessagesTableColumns[1]+") values(14,'鹿铃儿敲敲，小红帽儿高高，长胡子儿飘飘，圣诞节喧嚣热闹。看圣诞老人绝招，把幸福抓上雪橇，穿过烟囱迢迢，冲破寒风呼啸，誓送你快乐圣诞今朝!')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
