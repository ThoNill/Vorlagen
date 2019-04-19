package janusAngular2Backend.basis;

public abstract class ChildTag extends BasisTag {
    private BasisTag parent;

    public ChildTag(PageBasis basis, String name) {
        super(basis, name);
   }

    
    @Override
	public String getName() {
        return parent.getName() + "." + getName();
    }
 
    public String getChildName() {
        return super.getName();
    }
 
    
    public void setParent(BasisTag parent) {
        this.parent = parent;
    }
 

}