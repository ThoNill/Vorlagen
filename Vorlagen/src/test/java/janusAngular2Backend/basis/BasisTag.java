package janusAngular2Backend.basis;

public abstract class BasisTag {

    private PageBasis basis;
    private String name;

    public BasisTag(PageBasis basis, String name) {
        super();
        this.basis = basis;
        this.name = name;
    }

    public String getName() {
        return name;
    }   
    
    
    public abstract Ergebnis execute(Parameter parameter );

}