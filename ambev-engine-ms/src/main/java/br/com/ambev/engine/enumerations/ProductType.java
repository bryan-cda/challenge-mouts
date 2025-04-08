package br.com.ambev.engine.enumerations;

import java.util.Map;

public enum ProductType {
    STELLA_ARTOIS("Stella Artois", Map.of(
            330, "R$ 8,00",
            600, "R$ 14,00")),
    ORIGINAL("Original", Map.of(
            350, "R$ 5,50",
            600, "R$ 9,00")),
    MAGNIFICA("Magnífica", Map.of(
            350, "R$ 9,50",
            600, "R$ 15,00")),
    COLORADO("Colorado", Map.of(
            350, "R$ 10,00",
            600, "R$ 16,00")),
    PATAGONIA("Patagonia", Map.of(
            330, "R$ 11,00",
            600, "R$ 18,00")),
    CORONA("Corona", Map.of(
            330, "R$ 9,00",
            600, "R$ 15,00")),
    BUDWEISER("Budweiser", Map.of(
            350, "R$ 7,00",
            600, "R$ 12,00")),
    SERRANA("Serrana", Map.of(
            350, "R$ 6,50",
            600, "R$ 11,00")),
    BRAHMA("Brahma", Map.of(
            350, "R$ 5,00",
            600, "R$ 8,50")),
    POLAR("Polar", Map.of(
            350, "R$ 4,50",
            600, "R$ 7,50")),
    ANTARTICA("Antártica", Map.of(
            350, "R$ 4,00",
            600, "R$ 6,50")),
    BOHEMIA_PURO_MALTE("Bohemia Puro Malte", Map.of(
            350, "R$ 8,50",
            600, "R$ 14,00")),
    SKOL("Skol", Map.of(
            350, "R$ 4,20",
            600, "R$ 7,20")),
    HOEGAARDEN("Hoegaarden", Map.of(
            330, "R$ 15,00",
            600, "R$ 25,00")),
    BECKS("Beck's", Map.of(
            350, "R$ 7,50",
            600, "R$ 12,50")),
    MICHELOB("Michelob", Map.of(
            350, "R$ 12,00",
            600, "R$ 20,00")),
    LEGITIMA("Legítima", Map.of(
            350, "R$ 6,00",
            600, "R$ 10,00")),
    SPATEN("Spaten", Map.of(
            330, "R$ 13,00",
            600, "R$ 22,00")),
    QUILMES("Quilmes", Map.of(
            350, "R$ 6,00",
            600, "R$ 10,50")),
    BRAHMA_ZERO("Brahma Zero", Map.of(
            350, "R$ 5,80",
            600, "R$ 9,50")),
    BUDWEISER_0("Budweiser 0,0%", Map.of(
            350, "R$ 6,00",
            600, "R$ 10,00")),
    CORONA_0("Corona 0,0%", Map.of(
            350, "R$ 7,00",
            600, "R$ 12,00")),
    GUARANA_ANTARCTICA("Guaraná Antarctica", Map.of(
            350, "R$ 4,50",
            600, "R$ 7,50")),
    H2OH("H2OH!", Map.of(
            350, "R$ 3,50",
            600, "R$ 6,00")),
    SUKITA("Sukita", Map.of(
            350, "R$ 3,00",
            600, "R$ 5,00")),
    PEPSI("Pepsi", Map.of(
            350, "R$ 3,50",
            600, "R$ 6,00")),
    SODA_LIMONADA_ANTARCTICA("Soda Limonada Antarctica", Map.of(
            350, "R$ 3,00",
            600, "R$ 5,00")),
    TEEM("Teem", Map.of(
            350, "R$ 3,00",
            600, "R$ 5,00")),
    BEATS("Beats", Map.of(
            350, "R$ 6,50",
            600, "R$ 10,50")),
    FUSION("Fusion", Map.of(
            350, "R$ 7,00",
            600, "R$ 11,00")),
    GATORADE("Gatorade", Map.of(
            350, "R$ 5,00",
            600, "R$ 8,50")),
    BARÉ("Baré", Map.of(
            350, "R$ 1,80",
            600, "R$ 3,00")),
    CITRUS_ANTARCTICA("Citrus Antarctica", Map.of(
            350, "R$ 3,50",
            600, "R$ 6,00")),
    LIPTON_ICE_TEA("Lipton Ice Tea", Map.of(
            350, "R$ 5,00",
            600, "R$ 8,00")),
    AMA("Ama", Map.of(
            350, "R$ 1,50",
            600, "R$ 2,50")),
    DO_BEM("Do Bem", Map.of(
            350, "R$ 4,00",
            600, "R$ 6,50"));

    private final String name;
    private final Map<Integer, String> pricesBySize;

    // Construtor do enum
    ProductType(String name, Map<Integer, String> pricesBySize) {
        this.name = name;
        this.pricesBySize = pricesBySize;
    }

    // Método para pegar o nome do produto
    public String getName() {
        return name;
    }

    // Método para pegar o preço por tamanho (em ml)
    public String getPriceBySize(int size) {
        return pricesBySize.getOrDefault(size, "Preço não disponível");
    }

    // Método para obter o preço como valor numérico
    public double getPriceAsDouble(int size) {
        String price = pricesBySize.get(size);
        return price != null ? Double.parseDouble(price.replace("R$", "").replace(",", ".").trim()) : 0.0;
    }
}
