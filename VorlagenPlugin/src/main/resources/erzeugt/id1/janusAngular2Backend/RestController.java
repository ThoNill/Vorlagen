
package janusAngular2Backend;

import janusAngular2Backend.basis.PageBasis;
import janusAngular2Backend.basis.CallTag;
import janusAngular2Backend.basis.ColumnTag;
import janusAngular2Backend.basis.SetTag;
import janusAngular2Backend.basis.SqlTag;
import janusAngular2Backend.basis.BeanTag;
import janusAngular2Backend.basis.BatchTag;
import janusAngular2Backend.basis.Ergebnis;
import janusAngular2Backend.basis.Parameter;
import janusAngular2Backend.basis.ParameteImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.net.URI;
import java.util.Collection;
import java.util.HashMap;

import thomas.nill.AccountRepository;

@RestController
@RequestMapping("/api/{userId}/")
public class RestController  {

	private final AccountRepository accountRepository;
    private Page page;

	@Autowired
	RestController(  AccountRepository accountRepository) {
		this.accountRepository = accountRepository;
		this.page = new Page();

	}

	private void validateUser(String userId) {
		this.accountRepository.findByUsername(userId).orElseThrow(
				() -> new UserNotFoundException(userId));
	}

                              
     	@RequestMapping(method = RequestMethod.GET, value = "/")
     	Bookmark read(@PathVariable String userId,
     	   ) {
     		this.validateUser(userId);

     		HashMa<String,String> map = new HashMap<>();
     		Ergebnis ergebnis = page.execute("",new ParemeterImpl(map));

     		return ergebnis;
     	}
             
       	@RequestMapping(method = RequestMethod.PUT, value = "/")
       	Ergebnis do(@PathVariable String userId  , @RequestParam("") String   , @RequestParam("") String   , @RequestParam("") String   , @RequestParam("") String     ) {
       		this.validateUser(userId);

       		HashMa<String,String> map = new HashMap<>();
       		         
       		Ergebnis ergebnis = page.execute("",new ParemeterImpl(map));

       		return ergebnis;
       	}       
       	@RequestMapping(method = RequestMethod.PUT, value = "/")
       	Ergebnis do(@PathVariable String userId   ) {
       		this.validateUser(userId);

       		HashMa<String,String> map = new HashMap<>();
       		Ergebnis ergebnis = page.execute("",new ParemeterImpl(map));

       		return ergebnis;
       	}                            

}
 