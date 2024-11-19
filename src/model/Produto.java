package model;

public class Produto {

    private String produto;
    private int id;
    private int quantidade;
    private float valor;

    public Produto(String produto, int id, int quantidade, float valor) {
        this.produto = produto;
        this.id = id;
        this.quantidade = quantidade;
        this.valor = valor;
    }

    public String getProduto() {
        return produto;
    }

    public void setProduto(String produto) {
        this.produto = produto;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public float getValor() {
        return valor;
    }

    public void setValor(float valor) {
        this.valor = valor;
    }

    @Override
    public boolean equals(Object object) {

        Produto produto = (Produto) object;

        if(!this.produto.toLowerCase().equals(produto.getProduto().toLowerCase())) {
            return false;
        }
        if(this.id != produto.getId()) {
            return false;
        }
        if(this.quantidade != produto.getQuantidade()) {
            return false;
        }
        if(this.valor != produto.getValor()) {
            return false;
        }

        return true;
    }

    @Override
    public String toString() {
        return produto + ";" + id + ";" + quantidade + ";" + valor;
    }
}