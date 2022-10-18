import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import javax.swing.filechooser.FileSystemView;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.util.Scanner;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;
public  class Main {

    public static void main(String[] args) throws IOException, ParseException {
        DriveInfo();
  //      FileWork();
   //     FileXML();
//        ZIP();
   //     FileJSON();

    }

    public static void DriveInfo(){
        System.out.println("File system roots returned byFileSystemView.getFileSystemView():");
        FileSystemView fsv = FileSystemView.getFileSystemView();
        File[] roots = fsv.getRoots();
        for (int i = 0; i < roots.length; i++) {
            System.out.println("Root: " + roots[i]);
        }

        System.out.println("Home directory: " + fsv.getHomeDirectory());

        System.out.println("File system roots returned by File.listRoots():");
        File[] f = File.listRoots();
        for (int i = 0; i < f.length; i++) {
            System.out.println("Drive: " + f[i]);
            System.out.println("Display name: " + fsv.getSystemDisplayName(f[i]));
            System.out.println("Is drive: " + fsv.isDrive(f[i]));
            System.out.println("Is floppy: " + fsv.isFloppyDrive(f[i]));
            System.out.println("Readable: " + f[i].canRead());
            System.out.println("Writable: " + f[i].canWrite());
            System.out.println("Total space: " + f[i].getTotalSpace());
            System.out.println("Usable space: " + f[i].getUsableSpace());
        }
    }

    public static void FileWork(){
        Scanner in = new Scanner(System.in);

        int value=1;
        while (value!=0) {
            System.out.print("Выберите что вы хотите сделать\n"+
                    "1 Создать файл\n"+
                    "2 Добавить строчку в файл\n"+
                    "3 Прочитать файл\n"+
                    "4 Удалить файл\n"+
                    "0 Закончить работу с файлами\n");
            value = in.nextInt();
            if (value==1){
                File myFile = new File("C://test.txt");
                System.out.println("Имя файла" + myFile.getName());
            }
            if (value==2){
                try(FileWriter writer = new FileWriter("test.txt", false)) {
                    System.out.print("Введите строку");
                    String filename = in.next();
                    writer.write(filename);
                    writer.flush();
                }
                catch(IOException ex){
                    System.out.println(ex.getMessage());
                }
            }
            if (value==3) {
                try {
                    File file = new File("test.txt");
                    //создаем объект FileReader для объекта File
                    FileReader fr = new FileReader(file);
                    //создаем BufferedReader с существующего FileReader для построчного считывания
                    BufferedReader reader = new BufferedReader(fr);
                    // считаем сначала первую строку
                    String line = reader.readLine();
                    while (line != null) {
                        System.out.println(line);
                        // считываем остальные строки в цикле
                        line = reader.readLine();
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (value==4){
                File file = new File("C:\\test.txt");
                file.delete();
                System.out.print("Файл удалён\n ");
            }
            if (value==0){
                System.exit(0);
            }
        }
        in.close();
    }

    public static void FileJSON()throws IOException, ParseException{
        //Создание и запись в Файл
        String choose = "";
        File file = new File("Json.json");
        Scanner in = new Scanner(System.in);
        JSONObject JSStructure = new JSONObject();
        while (!choose.equals("e")) {
            System.out.println("Меню\n" +
                    "1 Создать файл\n" +
                    "2 Прочитать файл\n" +
                    "3 Удалить файл\n");
            System.out.print("Выбор ");
            choose = in.nextLine();
            System.out.println(choose);

            if(choose.equals("1"))
            {
                while(!choose.equals("4")) {
                    choose = "";
                    System.out.println("Меню Добавления\n" +
                            "1 Добавить: Ключ: Значение\n" +
                            "2 Добавить: Список\n" +
                            "3 Создать файл\n" +
                            "4 Выход\n");
                    System.out.print("Выбор ");
                    choose = in.next();


                    if (choose.equals("1")) {
                        String key = "";
                        String Value = "";
                        System.out.print("Ключ = ");
                        key = in.nextLine();
                        System.out.print("Значение = ");
                        Value = in.nextLine();
                        JSStructure.put(key, Value);

                    }

                    else if (choose.equals("2")) {
                        JSONArray list = new JSONArray();
                        String name = "";
                        int quant = 0;
                        System.out.print("Название списка = ");
                        name = in.next();
                        System.out.print("Кол-во значений = ");
                        quant = in.nextInt();
                        for (int i = 0; i < quant; i++) {
                            System.out.print("Значение"+" "+i+": ");
                            String Value = in.next();
                            list.add(Value);
                        }
                        JSStructure.put(name, list);

                    }
                    else if (choose.equals("3")) {
                        FileWriter FW = new FileWriter(file, false);
                        FW.write(JSStructure.toString());
                        FW.close();
                        System.out.println("Файл создан по пути: "+file.getAbsolutePath());
                    }
                }
            }
            else if(choose.equals("2"))
            {

                //Парсинг и вывод
                Reader reader = new FileReader(file);
                JSONParser parser = new JSONParser();
                JSONObject JSObj = (JSONObject) parser.parse(reader);
                System.out.println("Файл "+file.getName()+": "+JSObj.toJSONString());
                System.out.println("\n");
            }
            else if(choose.equals("3"))
            {
                File Del = new File(file.getAbsolutePath());
                Del.delete();
            }

        }
    }

    public static void FileXML() {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        Scanner scanner = new Scanner(System.in);
        int choose = 100;

        while (choose!= 0){
            System.out.println("Выберите что вы хотите сделать\n"+
                    "1 - Добавить новую запись и вывести всё на экран\n"+
                    "2 - Удалить файл\n"+
                    "0 - Закончить работу с XML");
            choose = scanner.nextInt();
            if (choose == 0){

            } else  if (choose == 2){
                File file = new File("Student.xml");
                file.delete();
                System.out.println("Файлик удалён");
            } else if (choose == 1){
                try{
                    builder = factory.newDocumentBuilder();

                    // создаем пустой объект Document, в котором будем
                    // создавать наш xml-файл
                    Document doc = builder.newDocument();
                    // создаем корневой элемент
                    Element rootElement =
                            doc.createElementNS("", "Список");
                    // добавляем корневой элемент в объект Document
                    doc.appendChild(rootElement);
                    int choose1 = 100;
                    int i=1;
                    while (choose1 !=0) {
                        System.out.println("\nВыберите что вы хотите сделать\n"+
                                "1 - Добавить студента\n"+
                                "0 - Вывести всё на экран");
                        choose1 = scanner.nextInt();

                        if(choose1 == 0){

                        } else if(choose1 == 1) {
                            // добавляем первый дочерний элемент к корневому
                            String id = Integer.toString(i);
                            System.out.println("Введите Имя");
                            String name = scanner.next();
                            System.out.println("Введите Возраст");
                            String age = scanner.nextLine();
                            rootElement.appendChild(getStudent(doc, id, name, age));
                        }
                        i++;
                    }
                    //создаем объект TransformerFactory для печати в консоль
                    TransformerFactory transformerFactory = TransformerFactory.newInstance();
                    Transformer transformer = transformerFactory.newTransformer();
                    // для красивого вывода в консоль
                    transformer.setOutputProperty(OutputKeys.INDENT, "yes");
                    DOMSource source = new DOMSource(doc);

                    //печатаем в консоль или файл
                    StreamResult console = new StreamResult(System.out);
                    StreamResult file = new StreamResult(new File("C://Users//zoxsp//IdeaProjects//Practice1.1//Student.xml"));

                    //записываем данные
                    transformer.transform(source, console);
                    transformer.transform(source, file);
                    System.out.println("Создание XML файла закончено");

                }catch(Exception e){
                    e.printStackTrace();
                }
            }

        }

    }
    
    private static Node getStudent(Document doc, String id, String name, String age) {
        Element Students = doc.createElement("Студенты");

        // устанавливаем атрибут id
        Students.setAttribute("id", id);

        // создаем элемент name
        Students.appendChild(getStudentElements(doc, Students, "Имя", name));

        // создаем элемент age
        Students.appendChild(getStudentElements(doc, Students, "Возраст", age));
        return Students;
    }

    // утилитный метод для создание нового узла XML-файла
    private static Node getStudentElements(Document doc, Element element, String name, String value) {
        Element node = doc.createElement(name);
        node.appendChild(doc.createTextNode(value));
        return node;
    }

    public static void ZIP() {
        Scanner in = new Scanner(System.in);
        System.out.print("Введите путь файла: ");
        String filename = in.nextLine();
        int value=3;
        while (value!=0)
        {
            if (value==3)
            {
                try(ZipOutputStream zout = new ZipOutputStream(new FileOutputStream("C:\\output.zip"));
                    FileInputStream fis= new FileInputStream(filename);)
                {
                    File fileToZip = new File(filename);
                    ZipEntry entry1=new ZipEntry(fileToZip.getName());
                    zout.putNextEntry(entry1);
                    // считываем содержимое файла в массив byte
                    byte[] buffer = new byte[fis.available()];
                    fis.read(buffer);
                    // добавляем содержимое к архиву
                    zout.write(buffer);
                    // закрываем текущую запись для новой записи
                    zout.closeEntry();
                }
                catch(Exception ex){

                    System.out.println(ex.getMessage());
                }}


            if (value==1)
            { try(ZipInputStream zin = new ZipInputStream(new FileInputStream("C:\\output.zip")))
            {
                ZipEntry entry;
                String name;
                long size;
                while((entry=zin.getNextEntry())!=null){

                    name = entry.getName(); // получим название файла
                    size=entry.getSize();  // получим его размер в байтах
                    System.out.printf("File name: %s \t File size: %d \n", name, size);

                    // распаковка
                    FileOutputStream fout = new FileOutputStream("C:\\new" + name);
                    for (int c = zin.read(); c != -1; c = zin.read()) {
                        fout.write(c);
                    }
                    fout.flush();
                    zin.closeEntry();
                    fout.close();
                    System.out.print("Архив распакован:\n ");
                }
            }
            catch(Exception ex){

                System.out.println(ex.getMessage());
            }
            }
            else if (value==2){
                File file = new File("C:\\output.zip");
                file.delete();
                System.out.print("Архив удален:\n ");
            }
            else if (value==0){System.exit(0);}
            System.out.print("Выберете действие:\n ");
            System.out.print("1-разархивировать:\n ");
            System.out.print("2-удалить:\n ");
            System.out.print("3-создать архив:\n ");
            System.out.print("0-выход:\n ");
            value = in.nextInt();

        }
        in.close();
    }
}