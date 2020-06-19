
package janusAngular2Backend;

import janusAngular2Backend.basis.PageBasis;
import janusAngular2Backend.basis.CallTag;
import janusAngular2Backend.basis.ColumnTag;
import janusAngular2Backend.basis.SetTag;
import janusAngular2Backend.basis.SqlTag;
import janusAngular2Backend.basis.BeanTag;
import janusAngular2Backend.basis.BatchTag;



public class Page extends PageBasis {

public void init() {

                              add( new SqlTag(this,"","")  
                .add(new ColumnTag(this,""))    )  ;        
        add(new BeanTag(this,"",class test.xml.wrap.BeispielWrap.class)  
           .add(new CallTag(this,"","")  .add(new SetTag(this,"","")) 
           .add(new SetTag(this,"","")) 
           .add(new SetTag(this,"","")) 
           .add(new SetTag(this,"","")) 
            )
           )  ;
        
        add(new BeanTag(this,"",class test.xml.wrap.BeispielWrap.class)  
           .add(new CallTag(this,"","")  )
           )  ;
             add( new BatchTag(this,"")  .get()  .          );                

}

} 