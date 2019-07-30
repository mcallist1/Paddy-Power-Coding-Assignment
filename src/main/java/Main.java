import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) throws IOException {

        String fileName = args[0];

        Converter converter = new Converter();
        List<String> list;
        Stream<String> stream = Files.lines(Paths.get(fileName));
        list = stream.collect(Collectors.toList());
        List<String> convertedList =  converter.convert(list);
        convertedList.forEach(System.out::println);
    }
}
