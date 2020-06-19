import { Component } from '@angular/core';

@Component({
  selector: 'page-',
  templateUrl : "Page.html",
})

export class PageComponent  { 
 name = 'Page'; 


 performCommand(command : String) {
     alert(command);
 }


}

