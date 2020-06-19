
package janusAngular2Backend;

import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;


public class reposPage  {

  @Autowired
  JdbcTemplate jdbcTemplate;

             
         public List<?>  () {
         return jdbcTemplate.query(
                       "", new Object[] { "Josh" },
                       (rs, rowNum) -> new Customer(rs.getLong("id"), rs.getString("first_name"), rs.getString("last_name"));
         }              
                                

}
 