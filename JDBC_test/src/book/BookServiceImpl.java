package book;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class BookServiceImpl implements BookService {

	Scanner sc = new Scanner(System.in);
	
	//BookDAO와 연결하기
	BookDAO bookDAO = new BookDAO();
	
	
	
	@Override
	public void startProgram() {


		//무한반복일때 조건을 true로 지정
		while (true) {

			//choice를 printMenu메소드에서 받아오기
			int choice = printMenu();

			switch(choice) {
			case 1 :
				System.out.println("1. 도서 정보 등록");
				insertBook();
				break;
			case 2 : 
				System.out.println("2. 도서 정보 수정");
				//updateMember();
				break;
			case 3 : 
				System.out.println("3. 도서 정보 삭제");
				//removeMember();
				break;
			case 4 : 
				System.out.println("4. 도서 정보 출력(이름)");
				printBook();
				break;
			case 5 : 
				System.out.println("5. 도서 전체 정보 출력");
				printAllBooks();
				break;
			case 6 : 
				System.out.println("프로그램 종료");
				break;
			default : 
				System.out.println("잘못된 숫자가 입력함, 1-6사이의 숫자 입력 가능");
				break;
			} 
		}
	}

	@Override
	public int printMenu() {
	
		System.out.println("===== 도서 관리 프로그램 =====");
		System.out.println("1. 도서 정보 등록");
		System.out.println("2. 도서 정보 수정");
		System.out.println("3. 도서 정보 삭제");
		System.out.println("4. 도서 정보 출력(이름)");
		System.out.println("5. 도서 전체 정보 출력");
		System.out.println(" 6. 프로그램 종료");
		System.out.println("[선택] : " );

		int choice = sc.nextInt();
		return choice;
	}
	
	//"1. 도서 정보 등록"
	public void insertBook() {
		
	
		System.out.println("도서명을 입력하세요");
		sc.nextLine();
		String title = sc.nextLine();
		System.out.print("도서가격을 입력하세요");
		int price = sc.nextInt();
		System.out.print("저자명을 입력하세요");
		sc.nextLine();
		String author = sc.nextLine();
		System.out.print("출판사를 입력하세요");
		String publisher = sc.nextLine();
		System.out.print("출판년도를 입력하세요");
		String pubYear = sc.nextLine();
		System.out.print("ISBN을 입력하세요");
		String isbn = sc.nextLine();
		System.out.print("총페이지을 입력하세요");
		int page = sc.nextInt();
		
		//입력값을 bookInfo에 넣기
		BookInfo bookInfo = new BookInfo();
		
		bookInfo.setTitle(title);
		bookInfo.setPrice(price);
		bookInfo.setAuthor(author);
		bookInfo.setPublisher(publisher);
		bookInfo.setPubYear(pubYear);
		bookInfo.setIsbn(isbn);
		bookInfo.setPage(page);
		
		//bookInfo에 담은 값을 bookDAO에 넣기
		int resultChk = 0;
		resultChk = bookDAO.insertBook(bookInfo);
		if(resultChk > 0 ) {
			System.out.println("도서 등록 완료");
		} else {System.out.println("도서 등록 실패");}
	}
	
	//"5. 도서 전체 정보 출력"
	public void printAllBooks(){
		List<HashMap<String, Object>> bookList = new ArrayList();	
		bookList = bookDAO.printAllBooks();
		
		System.out.println("도서명\t저자\t출판사\\t출판년도\\tISBN\\t페이지");
		
		for(int i = 0; i<bookList.size();i++) {
			System.out.println(bookList.get(i).get("book_title")+"\t");
			System.out.println(bookList.get(i).get("book_author")+"\t");
			System.out.println(bookList.get(i).get("book_publisher")+"\t");
			System.out.println(bookList.get(i).get("book_pubYear")+"\t");
			System.out.println(bookList.get(i).get("book_isbn")+"\t");
			System.out.println(bookList.get(i).get("book_page")+"\t");
		
		}
	}
	
	//"4. 도서 정보 출력(이름)"
	public void printBook() {
		List<HashMap<String, Object>> bookList = new ArrayList();
		
		
		System.out.println("검색할 도서명를 입력하세요 : ");
		String title = sc.next();
		bookList = bookDAO.printBook(title);
		
		System.out.println("도서명\t저자\t출판사\t출판년도\tISBN\t페이지");
		
		boolean flag = false;
		
		for(int i = 0; i<bookList.size();i++) {
			System.out.println(bookList.get(i).get("book_title")+"\t");
			System.out.println(bookList.get(i).get("book_author")+"\t");
			System.out.println(bookList.get(i).get("book_publisher")+"\t");
			System.out.println(bookList.get(i).get("book_pubYear")+"\t");
			System.out.println(bookList.get(i).get("book_isbn")+"\t");
			System.out.println(bookList.get(i).get("book_page")+"\t");
		
		}
		
		if (flag ==false) {
			System.out.println("등록된 도서가 없음");
		}
		
		
		
	}
	
	
	
	

}
