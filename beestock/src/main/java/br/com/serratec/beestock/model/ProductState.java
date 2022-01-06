package br.com.serratec.beestock.model;

public enum ProductState {
    NEW(1,"New Product"), USED(2,"Used Product");

    private Integer id;
    private String state;
    private ProductState(Integer id, String state) {
        this.id = id;
        this.state = state;
    }
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getState() {
        return state;
    }
    public void setState(String state) {
        this.state = state;
    }
}
