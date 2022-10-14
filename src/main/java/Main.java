import JDBC.ApiExplorer;
import JDBC.Member;
import JDBC.MemberService;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        ApiExplorer apiExplorer = new ApiExplorer();
        MemberService memberService = new MemberService();
//        memberService.registerAll(apiExplorer);
        memberService.getDistance(37.5544069, 126.8998666);


    }
}

