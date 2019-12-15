import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class runner {

    public static void main(String[] args) throws IOException {

        LocalDate dt = LocalDate.parse("2019-11-30");

        List<Integer> list = getResultFromDate(dt, "http://megalotto.pl/wyniki/lotto/");
        for (Integer integer : list) {
            System.out.print(integer + " ");
        }

        int year = dt.getYear();

        Map<Long,Integer> histogram = getHistogramFromYear(year, "http://megalotto.pl/wyniki/lotto/");
        System.out.println("\nhistogram in " + year + "\n"+ histogram + "\n");

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
        StringBuilder dateToFind = new StringBuilder();
        String dataToCheck = dateToFind.append(date.getDayOfMonth()).append("-").append(date.getMonthValue()).append("-").append(date.getYear()).toString();
        for (Element link : LiTags) {


            String currentDate = link.getElementsByClass("date_in_list").html();
            //String dataToCheck =   date.getDayOfMonth() + "-" +  date.getMonthValue() + "-" + date.getYear() ;

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
            System.out.println("Wyniiki losowania z :" + dateToFind);
        }
        else{
            System.out.println("Nie bylo losowania tego dnia, blad");
        }
        //System.out.println(content);


        return list;
    }

    static HashMap<Long,Integer> getHistogramFromYear(int year, String url){
        HashMap<Long,Integer> map = new HashMap<Long,Integer>();

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



        for (Element link : LiTags) {





//
                final int indexOfFirstNumber = 2;

                Elements luckyNumbers = link.getElementsByTag("li");
                for (int i = indexOfFirstNumber; i < 8; i++) {

                    //map.add(Integer.parseInt(luckyNumbers.get(i).html()));

                    map.merge(Long.parseLong(luckyNumbers.get(i).html()), 1, Integer::sum);
                    //System.out.println(map);
                }


            }


//        if(ifDateValid){
//            System.out.println("Wyniiki losowania z :" + dateToFind);
//        }
//        else{
//            System.out.println("Nie bylo losowania tego dnia, blad");
//        }
        //System.out.println(content);


        return map;
    }
}


