package spms.controls;

import java.util.Map;

import spms.bind.DataBinding;
import spms.dao.MemberDao;
import spms.vo.Member;

// 의존 객체 주입을 위해 인스턴스 변수와 셋터 메서드 추가
//- 또한 의존 객체를 꺼내는 기존 코드 변경
public class MemberUpdateController implements Controller, DataBinding {
  MemberDao memberDao;
  
  public MemberUpdateController setMemberDao(MemberDao memberDao) {
    this.memberDao = memberDao;
    return this;
  }
  
  @Override
	public Object[] getDataBinders() {
		return new Object[] {
			"no", Integer.class,
			"member", spms.vo.Member.class
		};
	}
  
  @Override
  public String execute(Map<String, Object> model) throws Exception {
	  Member member = (Member)model.get("member");
	  if (member.getEmail() == null) { 
      Integer no = (Integer)model.get("no");
      member = memberDao.selectOne(no);
      model.put("member", member);
      return "/member/MemberUpdateForm.jsp";

    } else {       
      memberDao.update(member);
      return "redirect:list.do";
    }
  }
}