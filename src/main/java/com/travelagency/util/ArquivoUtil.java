package com.travelagency.util;

import com.travelagency.exception.ArquivoProcessamentoException;
import com.travelagency.model.Viagem;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Utilitário para manipulação de Arquivos de Texto
 * Requisito: Leitura e gravação de arquivos texto
 * Requisito: Blocos try-catch-finally
 */
public class ArquivoUtil {
    
    private static final String DIRETORIO_RELATORIOS = "relatorios/";
    private static final LoggerUtil logger = new LoggerUtil();
    
    static {
        // Cria diretório se não existir (createDirectories é idempotente e thread-safe)
        try {
            Files.createDirectories(Paths.get(DIRETORIO_RELATORIOS));
            logger.info("Diretório de relatórios: " + DIRETORIO_RELATORIOS);
        } catch (IOException e) {
            logger.error("Erro ao criar diretório: " + e.getMessage());
        }
    }
    
    /**
     * Grava relatório de viagens em arquivo texto
     * Requisito: Gravação de arquivos texto
     * Requisito: Try-catch-finally
     */
    public static void gravarRelatorioViagens(List<Viagem> viagens, String nomeArquivo) {
        FileWriter writer = null;
        try {
            String caminhoCompleto = DIRETORIO_RELATORIOS + nomeArquivo + ".txt";
            writer = new FileWriter(caminhoCompleto, StandardCharsets.UTF_8);
            
            writer.write("=".repeat(80) + "\n");
            writer.write("RELATÓRIO DE VIAGENS - AGÊNCIA DE VIAGENS\n");
            writer.write("=".repeat(80) + "\n\n");
            writer.write(String.format("Total de viagens: %d\n\n", viagens.size()));
            
            for (int i = 0; i < viagens.size(); i++) {
                Viagem v = viagens.get(i);
                writer.write(String.format("Viagem %d:\n", i + 1));
                writer.write(String.format("  ID: %s\n", v.getId()));
                writer.write(String.format("  Destino: %s\n", v.getDestino()));
                writer.write(String.format("  Tipo: %s\n", v.getTipo().getDescricao()));
                writer.write(String.format("  Data Partida: %s\n", v.getDataPartida()));
                writer.write(String.format("  Data Retorno: %s\n", v.getDataRetorno()));
                writer.write(String.format("  Preço: R$ %.2f\n", v.getPreco()));
                writer.write(String.format("  Vagas: %d\n", v.getVagas()));
                writer.write(String.format("  Ativa: %s\n", v.getAtiva() ? "Sim" : "Não"));
                writer.write("\n");
            }
            
            writer.write("=".repeat(80) + "\n");
            logger.info("Relatório gravado com sucesso: " + caminhoCompleto);
            
        } catch (IOException e) {
            logger.error("Erro ao gravar relatório: " + e.getMessage());
            throw new ArquivoProcessamentoException("Erro ao gravar arquivo de relatório", DIRETORIO_RELATORIOS);
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                    logger.info("Arquivo fechado com sucesso");
                } catch (IOException e) {
                    logger.error("Erro ao fechar arquivo: " + e.getMessage());
                }
            }
        }
    }
    
    /**
     * Lê arquivo de configuração
     * Requisito: Leitura de arquivos texto
     * Requisito: Try-catch-finally
     */
    public static List<String> lerConfiguracao(String nomeArquivo) {
        List<String> linhas = new ArrayList<>();
        BufferedReader reader = null;
        
        try {
            String caminho = "config/" + nomeArquivo + ".txt";
            reader = new BufferedReader(new FileReader(caminho, StandardCharsets.UTF_8));
            
            String linha;
            while ((linha = reader.readLine()) != null) {
                if (!linha.trim().isEmpty() && !linha.startsWith("#")) {
                    linhas.add(linha.trim());
                }
            }
            
            logger.info("Arquivo de configuração lido com sucesso: " + caminho);
            
        } catch (FileNotFoundException e) {
            logger.warn("Arquivo de configuração não encontrado: " + nomeArquivo);
            throw new ArquivoProcessamentoException("Arquivo não encontrado", nomeArquivo);
        } catch (IOException e) {
            logger.error("Erro ao ler arquivo: " + e.getMessage());
            throw new ArquivoProcessamentoException("Erro ao ler arquivo de configuração");
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                    logger.info("Arquivo de leitura fechado");
                } catch (IOException e) {
                    logger.error("Erro ao fechar arquivo de leitura: " + e.getMessage());
                }
            }
        }
        
        return linhas;
    }
    
    /**
     * Grava log de operação
     */
    public static void gravarLog(String mensagem) {
        FileWriter writer = null;
        try {
            String caminhoCompleto = DIRETORIO_RELATORIOS + "operacoes.log";
            writer = new FileWriter(caminhoCompleto, true); // append mode
            writer.write(java.time.LocalDateTime.now() + " - " + mensagem + "\n");
            writer.flush();
            
        } catch (IOException e) {
            logger.error("Erro ao gravar log: " + e.getMessage());
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    logger.error("Erro ao fechar arquivo de log: " + e.getMessage());
                }
            }
        }
    }
}
