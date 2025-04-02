package br.com.ambev.engine.enumerations;

public enum ProductType {
    STELLA_ARTOIS("Stella Artois", "R$ 8,00"),
    ORIGINAL("Original", "R$ 5,50"),
    MAGNIFICA("Magnífica", "R$ 9,50"),
    COLORADO("Colorado", "R$ 10,00"),
    PATAGONIA("Patagonia", "R$ 11,00"),
    CORONA("Corona", "R$ 9,00"),
    BUDWEISER("Budweiser", "R$ 7,00"),
    SERRANA("Serrana", "R$ 6,50"),
    BRAHMA("Brahma", "R$ 5,00"),
    POLAR("Polar", "R$ 4,50"),
    ANTARTICA("Antártica", "R$ 4,00"),
    BOHEMIA_PURO_MALTE("Bohemia Puro Malte", "R$ 8,50"),
    SKOL("Skol", "R$ 4,20"),
    HOEGAARDEN("Hoegaarden", "R$ 15,00"),
    BECKS("Beck's", "R$ 7,50"),
    MICHELOB("Michelob", "R$ 12,00"),
    LEGITIMA("Legítima", "R$ 6,00"),
    SPATEN("Spaten", "R$ 13,00"),
    QUILMES("Quilmes", "R$ 6,00"),
    BRAHMA_ZERO("Brahma Zero", "R$ 5,80"),
    BUDWEISER_0("Budweiser 0,0%", "R$ 6,00"),
    CORONA_0("Corona 0,0%", "R$ 7,00"),
    GUARANA_ANTARCTICA("Guaraná Antarctica", "R$ 4,50"),
    H2OH("H2OH!", "R$ 3,50"),
    SUKITA("Sukita", "R$ 3,00"),
    PEPSI("Pepsi", "R$ 3,50"),
    SODA_LIMONADA_ANTARCTICA("Soda Limonada Antarctica", "R$ 3,00"),
    TEEM("Teem", "R$ 3,00"),
    BEATS("Beats", "R$ 6,50"),
    FUSION("Fusion", "R$ 7,00"),
    GATORADE("Gatorade", "R$ 5,00"),
    BARÉ("Baré", "R$ 1,80"),
    CITRUS_ANTARCTICA("Citrus Antarctica", "R$ 3,50"),
    LIPTON_ICE_TEA("Lipton Ice Tea", "R$ 5,00"),
    AMA("Ama", "R$ 1,50"),
    DO_BEM("Do Bem", "R$ 4,00");

    private final String name;
    private final String price;

    // Construtor do Enum
    ProductType(String name, String price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

    public double getPriceAsDouble() {
        return Double.parseDouble(price.replace("R$", "").replace(",", ".").trim());
    }

    @Override
    public String toString() {
        return String.format("%s - Preço Médio: %s", name, price);
    }
}
