import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

public class runner {

    public static void main(String[] args) throws IOException {

        LocalDate dt = LocalDate.parse("2019-11-30");

        List<Integer> list = getResultFromDate(dt, "http://megalotto.pl/wyniki/lotto/");
        for (Integer integer : list) {
            System.out.println(integer);
        }


    }

    static List<Integer> getResultFromDate(LocalDate date, String url){
        List<Integer> list = new LinkedList<>();

        int year = date.getYear();
        url = url + "losowania-z-roku-" + year;

        Document document = null;
        try {
            document = Jsoup.connect(url).get();

        } catch (IOException e) {
            e.printStackTrace();
        }

        Elements content = document.getElementsByClass("lista_ostatnich_losowan");

        Element data = content.get(0);
        Elements LiTags = data.getElementsByTag("ul");

        boolean ifDateValid = false;

        for (Element link : LiTags) {


            String currentDate = link.getElementsByClass("date_in_list").html();
            //String dataToCheck =   date.getDayOfMonth() + "-" +  date.getMonthValue() + "-" + date.getYear() ;
            StringBuilder datee = new StringBuilder();
            String dataToCheck = datee.append(date.getDayOfMonth()).append("-").append(date.getMonthValue()).append("-").append(date.getYear()).toString();
            //System.out.println(currentDate + " and " + dataToCheck);


            if(currentDate.equals(dataToCheck))
            {
                final int indexOfFirstNumber = 2;
                System.out.println(currentDate + " We got that date !" + dataToCheck );
                Elements luckyNumbers = link.getElementsByTag("li");
                for (int i = indexOfFirstNumber; i < 8; i++) {

                    list.add(Integer.parseInt(luckyNumbers.get(i).html()));
                }
                ifDateValid = true;

            }
        }

        if(ifDateValid){
            System.out.println("we got this");
        }
        else{
            System.out.println("Nie bylo losowania tego dnia, blad");
        }
        //System.out.println(content);


        return list;
    }
}


