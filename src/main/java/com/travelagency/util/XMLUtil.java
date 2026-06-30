package com.travelagency.util;

import com.travelagency.model.Cliente;
import com.travelagency.model.Viagem;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.StringWriter;
import java.util.List;

/**
 * Utilitário para manipulação de XML
 * Requisito: Manipulação de XML
 * Requisito: Blocos try-catch-finally
 */
public class XMLUtil {
    
    private static final LoggerUtil logger = new LoggerUtil();
    
    /**
     * Converte lista de viagens para XML
     * Requisito: Manipulação de XML
     */
    public static String viagensParaXML(List<Viagem> viagens) {
        StringWriter stringWriter = null;
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.newDocument();
            
            // Elemento raiz
            Element rootElement = doc.createElement("viagens");
            rootElement.setAttribute("total", String.valueOf(viagens.size()));
            rootElement.setAttribute("xmlns", "http://www.travelagency.com/viagens");
            doc.appendChild(rootElement);
            
            // Adiciona cada viagem
            for (Viagem viagem : viagens) {
                Element viagemElement = doc.createElement("viagem");
                
                addElement(doc, viagemElement, "id", viagem.getId());
                addElement(doc, viagemElement, "destino", viagem.getDestino());
                addElement(doc, viagemElement, "tipo", viagem.getTipo().getDescricao());
                addElement(doc, viagemElement, "dataPartida", viagem.getDataPartida().toString());
                addElement(doc, viagemElement, "dataRetorno", viagem.getDataRetorno().toString());
                addElement(doc, viagemElement, "preco", viagem.getPreco().toString());
                addElement(doc, viagemElement, "vagas", viagem.getVagas().toString());
                addElement(doc, viagemElement, "ativa", viagem.getAtiva().toString());
                
                rootElement.appendChild(viagemElement);
            }
            
            // Converte Document para String
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty("indent", "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            
            stringWriter = new StringWriter();
            transformer.transform(new DOMSource(doc), new StreamResult(stringWriter));
            
            logger.info("Viagens convertidas para XML com sucesso");
            return stringWriter.toString();
            
        } catch (Exception e) {
            logger.error("Erro ao converter viagens para XML: " + e.getMessage());
            return "<erro>Erro ao converter viagens para XML</erro>";
        } finally {
            if (stringWriter != null) {
                try {
                    stringWriter.close();
                } catch (Exception e) {
                    logger.error("Erro ao fechar StringWriter: " + e.getMessage());
                }
            }
        }
    }
    
    /**
     * Converte cliente para XML
     */
    public static String clienteParaXML(Cliente cliente) {
        StringWriter stringWriter = null;
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.newDocument();
            
            Element clienteElement = doc.createElement("cliente");
            clienteElement.setAttribute("xmlns", "http://www.travelagency.com/cliente");
            
            addElement(doc, clienteElement, "id", cliente.getId());
            addElement(doc, clienteElement, "nome", cliente.getNome());
            addElement(doc, clienteElement, "email", cliente.getEmail());
            addElement(doc, clienteElement, "telefone", cliente.getTelefone());
            addElement(doc, clienteElement, "cpf", cliente.getCpf());
            addElement(doc, clienteElement, "idade", cliente.getIdade().toString());
            addElement(doc, clienteElement, "renda", cliente.getRenda().toString());
            addElement(doc, clienteElement, "ativo", cliente.getAtivo().toString());
            
            doc.appendChild(clienteElement);
            
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty("indent", "yes");
            
            stringWriter = new StringWriter();
            transformer.transform(new DOMSource(doc), new StreamResult(stringWriter));
            
            logger.info("Cliente convertido para XML com sucesso");
            return stringWriter.toString();
            
        } catch (Exception e) {
            logger.error("Erro ao converter cliente para XML: " + e.getMessage());
            return "<erro>Erro ao converter cliente para XML</erro>";
        } finally {
            if (stringWriter != null) {
                try {
                    stringWriter.close();
                } catch (Exception e) {
                    logger.error("Erro ao fechar StringWriter: " + e.getMessage());
                }
            }
        }
    }
    
    /**
     * Método auxiliar para adicionar elementos XML
     */
    private static void addElement(Document doc, Element parent, String tagName, String textContent) {
        Element element = doc.createElement(tagName);
        element.appendChild(doc.createTextNode(textContent != null ? textContent : ""));
        parent.appendChild(element);
    }
    
    /**
     * Salva XML em arquivo
     */
    public static void salvarXMLArquivo(String xml, String nomeArquivo) {
        try {
            String nomeSanitizado = nomeArquivo.replaceAll("[^a-zA-Z0-9_\\-]", "_");
            String caminhoCompleto = "relatorios/" + nomeSanitizado + ".xml";
            File file = new File("relatorios", nomeSanitizado + ".xml").getCanonicalFile();
            if (!file.getParentFile().getCanonicalPath().equals(new File("relatorios").getCanonicalPath())) {
                throw new SecurityException("Caminho de arquivo invalido: " + nomeArquivo);
            }
            
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty("indent", "yes");
            transformer.transform(
                new javax.xml.transform.stream.StreamSource(new java.io.StringReader(xml)),
                new StreamResult(file)
            );
            
            logger.info("XML salvo com sucesso: " + caminhoCompleto);
        } catch (Exception e) {
            logger.error("Erro ao salvar XML: " + e.getMessage());
        }
    }
}
