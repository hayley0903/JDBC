package parking;

import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class ParkingServiceImpl implements ParkingService{

	public static ParkingDAO parkingDAO = new ParkingDAO();
	public static Scanner sc = new Scanner(System.in);
	@Override
	public void startProgram() {
		// TODO Auto-generated method stub
		while (true) {

			int menu = printMenu();

			switch(menu) {

			case 1 :
				inParking();
				break;

			case 2 :
				outParking();
				break;

			case 3:
				System.out.println("시스템 종료 !!");
				System.exit(0);

				break;

			default  :
				System.out.println("1~3사이의 수를 선택해 주세요.");
				break;
			}
		}
	}

	public int printMenu() {
		System.out.println("[ 주차장 현황 ]");
		System.out.println();

		List<HashMap<String, Object>> parkingList = parkingDAO.selectParkingSpace();
		for(int i = 0; i<parkingList.size(); i++) {
			int parkingLocationY = Integer.parseInt(parkingList.get(i).get("parkingY").toString());
			String parkingYn = parkingList.get(i).get("parkingYn").toString();
			int parkingNumber = Integer.parseInt(parkingList.get(i).get("parkingNumber").toString());
			if(parkingLocationY == 1) {
				System.out.println("");
			}
			if("Y".equals(parkingYn)) {
				if(parkingNumber > 10) {
					System.out.print("■ " + parkingNumber+ " : " + parkingList.get(i).get("parkingYn") + "\t\t");
				}else {
					System.out.print("■ " + parkingNumber+ " : " + parkingList.get(i).get("parkingYn") + "\t\t\t");
				}
			}else {
				if(parkingNumber > 10) {
					System.out.print("□ " + parkingNumber+ " : " + parkingList.get(i).get("parkingYn") + "\t\t");
				}else {
					System.out.print("□ " + parkingNumber+ " : " + parkingList.get(i).get("parkingYn") + "\t\t\t");
				}
			}
		}

		System.out.println();
		System.out.println("원하는 메뉴를 선택해 주세요.");
		System.out.println("1) 주차	2) 출차 	3)종료 ");
		int menu = sc.nextInt();

		return menu;
	}

	public void inParking() {
		
		String inOut = "주차";
		
		System.out.println("주차하고 싶은 위치를 선택해 주세요");
		System.out.println("(예시) 주차 공간 선택 - 1");

		int location = sc.nextInt();

		HashMap<String, Object> parkingMap = new HashMap<String, Object>();
		parkingMap = parkingDAO.selectParking(location);
		

		if (parkingMap.get("parkingNumber") == null) {
			System.out.println("주차 위치를 잘못 입력하셨습니다.");
			System.out.println("처음부터 다시 시작하세요.");
			return;
		} else if ("Y".equals(parkingMap.get("parkingYn").toString())) { 	
			System.out.println("이미 다른 차량이 주차되어 있습니다.");
			System.out.println("처음부터 다시 시작하세요.");
		}
		else {
			System.out.print("주차 차량의 번호를 입력해 주세요. (예: 20가1234) : ");
			String carNum = sc.next();

			System.out.print("차량 번호가 " + carNum + " 맞습니까? (y / n)");
			String confirm = sc.next();

			if ("y".equals(confirm.toLowerCase())) {
				parkingDAO.insertParking(location, carNum);
				//주차이력등록
				parkingDAO.parkingHistory(location, carNum, inOut);
				System.out.println("주차가 완료되었습니다 !!");
			} else {
				System.out.println("처음부터 다시 시작하세요.");
			}
		}
	}

	public void outParking() {

		String inOut = "출차";
		
		System.out.print("주차 위치를 입력해 주세요. : ");
		int location = sc.nextInt();
		
	
		int chk = 0;
		//출차이력등록
		HashMap<String, Object> parkingMap = new HashMap<String, Object>();
		parkingMap = parkingDAO.selectParking(location);
		parkingDAO.parkingHistory(location, parkingMap.get("parkingCarNumber").toString(), inOut);
		chk = parkingDAO.deleteParking(location);
		if(chk > 0 ) {
			System.out.println("출치 완료");
		} else {System.out.println("출차 실패");}
	}

	

	
	
	
	
}
