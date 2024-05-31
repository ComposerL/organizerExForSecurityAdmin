$(document).ready(() => {
    console.log( "[ADMIN LIST] DOCUMENT READY!" );
    
    eventInit();
    
});

const eventInit = () => {
   console.log("[ADMIN LIST] eventInit()");
   
   $(document).on('change', '#article_wrap select', (e) => {
      console.log('[ADMIN LIST] SELECT CHANGED!!');
      
      ajaxSetAdminRole(e.target.dataset.a_no, e.target.value);
      
   })
   
}

const ajaxSetAdminRole = (no, role) => {
   console.log("[ADMIN LIST] ajaxSetAdminRole()", no, role);
   
   let msg = {
      a_no: no,
      a_role: role
   };
   
   $.ajax({
      url: '/admin/adminModifyForRole',
      type: 'PUT',
      data: JSON.stringify(msg),
      dataType: 'json',
      contentType: 'application/json; charset=utf-8',
      success: (data) => {
         console.log('[ADMIN LIST] AJAX COMMUNICATION SUCCESS - ajaxSetAdminRole()');
         
         if (data.resultModifyForRole > 0) {
            console.log('[ADMIN LIST] ROLE UPDATE SUCCESS!!');
            
            alert("ADMIN ROLE UPDATE SUCCESS!!");
            
         } else {
            console.log('[ADMIN LIST] ROLE UPDATE FAIL!!');
            
            alert("ADMIN ROLE UPDATE FAIL!!");
            
         }
         
      },
      error: (error) => {
         console.log('[ADMIN LIST] AJAX COMMUNICATION ERROR - ajaxSetMemberRole()');
         
      }
   });
   
}