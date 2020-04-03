package repositories;

import domain.Entity;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import validators.ValidationException;
import validators.Validator;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.IOException;

public abstract class AbstractXMLRepository<ID, E extends Entity<ID>> extends AbstractFileRepository {
    String filepath;

    public AbstractXMLRepository(Validator validator, String fileName) {
        super(validator, fileName);
        this.filepath = fileName;
        load();
    }

    @Override
    public void write() {
        try{
            Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();

            Element radacina = document.createElement("students");
            document.appendChild(radacina);

            super.findAll().forEach(s -> {
                Element e = createElementfromEntity(document, (E) s);
                radacina.appendChild(e);
            });

            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            Source source = new DOMSource(document);
            transformer.transform(source, new StreamResult(filepath));

        } catch (ParserConfigurationException |  TransformerException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void load() {
        try {
            Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(this.filepath);

            Element radacina = document.getDocumentElement();
            NodeList children = radacina.getChildNodes();

            for (int i = 0; i < children.getLength(); i++) {

                Node elem = children.item(i);

                if (elem instanceof Element) {
                    E entity = createEntityFromElement((Element) elem);

                    try {
                        super.save(entity);
                    } catch (ValidationException | NumberFormatException x) {
                        continue;
                    }
                }
            }
        } catch (ParserConfigurationException | IOException | SAXException x){
            x.printStackTrace();
        }
    }

    protected abstract Element createElementfromEntity(Document document, E entity);

    protected abstract E createEntityFromElement(Element entityElement);
}

