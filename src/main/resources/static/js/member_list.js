$(document).ready(() => {
    console.log( "[MEMBER LIST] DOCUMENT READY!" );
    
    eventInit();
    
});

const eventInit = () => {
   console.log("[MEMBER LIST] eventInit()");
   
   $(document).on('change', '#article_wrap select', (e) => {
      console.log('[MEMBER LIST] SELECT CHANGED!!');
      
      ajaxSetMemberRole(e.target.dataset.m_no, e.target.value);
      
   })
   
}

const ajaxSetMemberRole = (no, role) => {
   console.log("[MEMBER LIST] ajaxSetMemberRole()", no, role);
   
   let msg = {
      m_no: no,
      m_role: role
   };
   
   $.ajax({
      url: '/member/memberModifyForRole',
      type: 'PUT',
      data: JSON.stringify(msg),
      dataType: 'json',
      contentType: 'application/json; charset=utf-8',
      success: (data) => {
         console.log('[MEMBER LIST] AJAX COMMUNICATION SUCCESS - ajaxSetMemberRole()');
         
         if (data.resultModifyForRole > 0) {
            console.log('[MEMBER LIST] ROLE UPDATE SUCCESS!!');
            
            alert("MEMBER ROLE UPDATE SUCCESS!!");
            
         } else {
            console.log('[MEMBER LIST] ROLE UPDATE FAIL!!');
            
            alert("MEMBER ROLE UPDATE FAIL!!");
            
         }
         
      },
      error: (error) => {
         console.log('[MEMBER LIST] AJAX COMMUNICATION ERROR - ajaxSetMemberRole()');
         
      }
   });
   
}