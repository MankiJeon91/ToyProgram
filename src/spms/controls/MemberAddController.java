package spms.controls;

import java.util.Map;

import spms.annotation.Component;
import spms.bind.DataBinding;
import spms.dao.MemberDao;
import spms.vo.Member;

// 의존 객체 주입을 위해 인스턴스 변수와 셋터 메서드 추가
//- 또한 의존 객체를 꺼내는 기존 코드 변경
@Component("/member/add.do")
public class MemberAddController implements Controller, DataBinding {
  MemberDao memberDao;
  
  public MemberAddController setMemberDao(MemberDao memberDao) {
    this.memberDao = memberDao;
    return this;
  }
  
  @Override
	public Object[] getDataBinders() {
		return new Object[] {
			"member", spms.vo.Member.class	
		};
	}
  
  @Override
  public String execute(Map<String, Object> model) throws Exception {
	  Member member = (Member)model.get("member");
    if (member.getEmail() == null) { // 입력폼을 요청할 때
      return "/member/MemberForm.jsp";
      
    } else { // 회원 등록을 요청할 때      
      memberDao.insert(member);
      return "redirect:list.do";
    }
  }
}
