package br.com.processingestion.controler;

import static java.util.Arrays.asList;

import java.util.Random;

import org.apache.camel.FluentProducerTemplate;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
@RestController
public class RestControler  {
	
	private final FluentProducerTemplate fluentProducerTemplate;
	
	@RequestMapping(value = "/hello/{tribunal}", method = RequestMethod.GET)
	public String getProcess(@PathVariable String tribunal) {
		
		this.mongoDBConnection(tribunal);
		
		
		System.out.println("test");
		return fluentProducerTemplate.to("direct:db-ingestion").request(String.class);
		
	}

	public RestControler(FluentProducerTemplate fluentProducerTemplate) {
		super();
		this.fluentProducerTemplate = fluentProducerTemplate;
		
	}
	private MongoDatabase mongoDBConnection(String tribunal) {
		StringBuilder uri = new StringBuilder("mongodb+srv://adm:lfnEHVxQoRX1AX8U@cluster0.gs78v.mongodb.net/")
				.append(tribunal)
				.append("?retryWrites=true&w=majority");
		try (MongoClient mongoClient = MongoClients.create(uri.toString())) {
            MongoDatabase mongoDB = mongoClient.getDatabase(tribunal);
            
            MongoCollection<Document> ProcessosCollection = mongoDB.getCollection("processo");
    		
    		Random rand = new Random();
            Document student = new Document("_id", new ObjectId());
            student.append("student_id", 10000d)
                   .append("class_id", 1d)
                   .append("scores", asList(new Document("type", "exam").append("score", rand.nextDouble() * 100),
                                            new Document("type", "quiz").append("score", rand.nextDouble() * 100),
                                            new Document("type", "homework").append("score", rand.nextDouble() * 100),
                                            new Document("type", "homework").append("score", rand.nextDouble() * 100)));
            ProcessosCollection.insertOne(student);
            
            return mongoDB;

        }
	}

}
