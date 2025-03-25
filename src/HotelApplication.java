import menus.MainMenu;


public class HotelApplication {
    public static void main(String[] args) {
        final MainMenu mainMenu = MainMenu.getInstance();
        mainMenu.showMenu();
    }
}
