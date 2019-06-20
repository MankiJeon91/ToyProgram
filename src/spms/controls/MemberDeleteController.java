package spms.controls;

import java.util.Map;

import spms.annotation.Component;
import spms.bind.DataBinding;
import spms.dao.MemberDao;

// 의존 객체 주입을 위해 인스턴스 변수와 셋터 메서드 추가
//- 또한 의존 객체를 꺼내는 기존 코드 변경
@Component("/member/delete.do")
public class MemberDeleteController implements Controller, DataBinding {
  MemberDao memberDao;
  
  public MemberDeleteController setMemberDao(MemberDao memberDao) {
    this.memberDao = memberDao;
    return this;
  }
  
  @Override
	public Object[] getDataBinders() {
		return new Object[] {
			"no", Integer.class	
		};
	}
  
  @Override
  public String execute(Map<String, Object> model) throws Exception {
    Integer no = (Integer)model.get("no");
    memberDao.delete(no);
    
    return "redirect:list.do";
  }
}
