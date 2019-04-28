package jobs;

import model.Person;
import org.springframework.batch.item.ItemProcessor;

public class XmlToMysqlProcessor implements ItemProcessor<Person,Person> {

    public Person process(Person person) throws Exception {
        System.out.println("inside XmlToMysqlProcessor");
        if(person.getFname().trim().equals(""))
            return null;
        return person;
    }
}
