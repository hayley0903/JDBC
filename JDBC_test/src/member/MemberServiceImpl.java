package member;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class MemberServiceImpl implements MemberService {

	MemberDAO mDAO = new MemberDAO();
	
	Scanner sc = new Scanner(System.in);
	
	
	
	@Override
	public void startProgram() {
		int count = 0;

		while (true) {

			int choice = printMenu();

			switch(choice) {
			case 1 :
				displayMsg("1. 회원 정보 등록");
				insertMember();
				break;
			case 2 :
				displayMsg("2. 회원 정보 수정");
				updateMember();
				break;
			case 3 :
				displayMsg("3. 회원 정보 삭제");
				deleteMember();
				break;
			case 4 :
				displayMsg("4. 회원 정보 출력(이름)");
				printMember();
				break;
			case 5 :
				displayMsg("5. 회원 전체 정보 출력");
				printAllMembers();
				break;
			case 6 :
				displayMsg("프로그램 종료 ~!!");
				count++;
				break;
			default :
				// "잘못된 숫자가 입력됨. 1~6 사이의 숫자 입력 가능");
				break;
			}

			if (count == 1) {
				break;
			}
		}
		
	}

	
	@Override
	public int printMenu() {
		displayMsg("===== 회원 관리 프로그램 =====");
		displayMsg("1. 회원 정보 등록");
		displayMsg("2. 회원 정보 수정");
		displayMsg("3. 회원 정보 삭제");
		displayMsg("4. 회원 정보 출력(이름)");
		displayMsg("5. 회원 전체 정보 출력");
		displayMsg(" 6. 프로그램 종료");
		displayMsg("[선택] : " );

		int choice = sc.nextInt();
		return choice;
	}
	
	//"1. 회원 정보 등록"
	public void insertMember() {
		
	
		//회원정보 입력받기
		System.out.print("아이디");
		String memberId = sc.next();

		System.out.print("비밀번호");
		String memberPw = sc.next();

		System.out.print("이름");
		String memberName = sc.next();
		
		System.out.print("생년월일");
		String memberBirth = sc.next();

		System.out.print("이메일");
		String memberEmail = sc.next();

		System.out.print("연락처");
		String memberPhone = sc.next();
		
		Member member = new Member();

		//회원정보 세팅
		member.setMemberId(memberId);
		member.setMemberPw(memberPw);
		member.setMemberName(memberName);
		member.setMemberBirth(memberBirth);
		member.setMemberEmail(memberEmail);
		member.setMemberPhone(memberPhone);
		
		
		
		int chk = 0;
		chk = mDAO.insertMember(member);
		if(chk > 0 ) {
			System.out.println("회원정보 등록 완료");
		} else {System.out.println("회원정보 등록 실패");}
		
	}
	

	//"5. 회원 전체 정보 출력"
	public void printAllMembers() {
		List<HashMap<String, Object>> memberList = new ArrayList();
		memberList = mDAO.printAllMembers();
		
		System.out.println("회원IDX\t회원ID\t비밀번호\t이름\t생년월일\t이메일\t연락처");
		for(int i = 0; i<memberList.size(); i++) {
			System.out.print(memberList.get(i).get("member_idx")+"\t");
			System.out.print(memberList.get(i).get("member_id")+"\t");
			System.out.print(memberList.get(i).get("member_pw")+"\t");
			System.out.print(memberList.get(i).get("member_name")+"\t");
			System.out.print(memberList.get(i).get("member_birth")+"\t");
			System.out.print(memberList.get(i).get("member_email")+"\t");
			System.out.println(memberList.get(i).get("member_phone")+"\t");
		}
		
	}
	
	
	//"4. 회원 정보 출력(이름)"
	public void printMember() {
		
		System.out.println("검색할 회원ID를 입력하시오");
		String memberID = sc.next();
		
		
		List<HashMap<String, Object>> memberList = new ArrayList();
		memberList = mDAO.printMember(memberID);
		
		System.out.println("회원IDX\t회원ID\t비밀번호\t이름\t생년월일\t이메일\t연락처");
		for(int i = 0; i<memberList.size(); i++) {
			System.out.print(memberList.get(i).get("member_idx")+"\t");
			System.out.print(memberList.get(i).get("member_id")+"\t");
			System.out.print(memberList.get(i).get("member_pw")+"\t");
			System.out.print(memberList.get(i).get("member_name")+"\t");
			System.out.print(memberList.get(i).get("member_birth")+"\t");
			System.out.print(memberList.get(i).get("member_email")+"\t");
			System.out.println(memberList.get(i).get("member_phone")+"\t"); 
			}
	}
	
	//"2. 회원 정보 삭제"
	public void deleteMember() {
		System.out.println("삭제할 회원ID를 입력하시오");
		String memberID = sc.next();
		
//		List<HashMap<String, Object>> memberList = new ArrayList();
//		memberList = mDAO.printMember(memberID);
		
		List<HashMap<String, Object>> memberList = mDAO.printMember(memberID);
		
		System.out.println("회원IDX\t회원ID\t비밀번호\t이름\t생년월일\t이메일\t연락처");
		for(int i = 0; i<memberList.size(); i++) {
			System.out.print(memberList.get(i).get("member_idx")+"\t");
			System.out.print(memberList.get(i).get("member_id")+"\t");
			System.out.print(memberList.get(i).get("member_pw")+"\t");
			System.out.print(memberList.get(i).get("member_name")+"\t");
			System.out.print(memberList.get(i).get("member_birth")+"\t");
			System.out.print(memberList.get(i).get("member_email")+"\t");
			System.out.println(memberList.get(i).get("member_phone")+"\t"); 
			}
		
		
		System.out.println("삭제할 순번을 입력하시오");
		int num = sc.nextInt();
		int deleteNum = Integer.parseInt(memberList.get(num-1).get("member_idx").toString());
		

		int chk = 0;
		chk = mDAO.deleteMember(deleteNum);
		if(chk > 0 ) {
			System.out.println("회원정보 삭제 완료");
		} else {System.out.println("회원정보 삭제 실패");}
		
				
	}
	
	//"2. 회원 정보 수정"
	public void updateMember() {

		System.out.println("검색할 회원ID를 입력하시오");
		String memberID = sc.next();
		
		
//		List<HashMap<String, Object>> memberList = new ArrayList();
//		memberList = mDAO.printMember(memberID);
//		
		List<HashMap<String, Object>> memberList = mDAO.printMember(memberID);
		
		System.out.println("회원IDX\t회원ID\t비밀번호\t이름\t생년월일\t이메일\t연락처");
		for(int i = 0; i<memberList.size(); i++) {
			System.out.print(memberList.get(i).get("member_idx")+"\t");
			System.out.print(memberList.get(i).get("member_id")+"\t");
			System.out.print(memberList.get(i).get("member_pw")+"\t");
			System.out.print(memberList.get(i).get("member_name")+"\t");
			System.out.print(memberList.get(i).get("member_birth")+"\t");
			System.out.print(memberList.get(i).get("member_email")+"\t");
			System.out.println(memberList.get(i).get("member_phone")+"\t"); 
			}
		
		
		System.out.println("수정할 순번을 입력하시오");
		int num = sc.nextInt();
		int memberIdx = Integer.parseInt(memberList.get(num-1).get("member_idx").toString());
		
		System.out.println("수정할 회원명을 입력하세요");
		String updateName = sc.next();
		
		int chk = 0;
		chk = mDAO.updateMember(memberIdx, updateName);
		if(chk > 0 ) {
			System.out.println("회원정보 수정 완료");
		} else {System.out.println("회원정보 수정 실패");}
	}
	
	public void displayMsg(String msg) {
		System.out.println(msg);	
	}

}
