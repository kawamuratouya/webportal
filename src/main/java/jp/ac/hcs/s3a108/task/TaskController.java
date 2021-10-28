package jp.ac.hcs.s3a108.task;

import java.io.IOException;
import java.security.Principal;
import java.sql.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import jp.ac.hcs.s3a108.WebConfig;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Controller
public class TaskController {
	
	@Autowired
	private TaskService taskService;

	public static final String JUDGE_DATE = 
			"^[0-9]{4}-(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01])$";
	
	@PostMapping("/task")
	public String getTask(Principal principal,Model model,ModelAndView mav) {
		String returns = null;
		TaskEntity taskEntity = taskService.getTask(principal.getName());
		model.addAttribute("taskEntity",taskEntity);
		
		mav.setViewName("task");
		mav.addObject("isresult",false);
		
		returns = "task/task";
		return returns;
	}
	@PostMapping("/task/csv")
	public ResponseEntity<byte[]> getTaskCsv(Principal principal, Model model) {

		final String OUTPUT_FULLPATH = WebConfig.OUTPUT_PATH + WebConfig.FILENAME_TASK_CSV;

		log.info("[" + principal.getName() + "]CSVファイル作成:" + OUTPUT_FULLPATH);

		// CSVファイルをサーバ上に作成
		taskService.taskListCsvOut(principal.getName());

		// CSVファイルをサーバから読み込み
		byte[] bytes = null;
		try {
			bytes = taskService.getFile(OUTPUT_FULLPATH);
			log.info("[" + principal.getName() + "]CSVファイル読み込み成功:" + OUTPUT_FULLPATH);
		} catch (IOException e) {
			log.warn("[" + principal.getName() + "]CSVファイル読み込み失敗:" + OUTPUT_FULLPATH);
			e.printStackTrace();
		}

		// CSVファイルのダウンロード用ヘッダー情報設定
		HttpHeaders header = new HttpHeaders();
		header.add("Content-Type", "text/csv; charset=UTF-8");
		header.setContentDispositionFormData("filename", WebConfig.FILENAME_TASK_CSV);

		// CSVファイルを端末へ送信
		return new ResponseEntity<byte[]>(bytes, header, HttpStatus.OK);
	}  	  
	  @RequestMapping("/task/delete/{task.id}")
		public String deleteTask(@PathVariable("task.id") int id,
				Principal principal,Model model,ModelAndView mav){
			
			System.out.println("御陀仏");
			
			/*チェック（あとでやる）
			 * if (comment == null || comment.length() >= 50 || limitday == null ) {
			 * 
			 * }
			 */
			
			
			taskService.deleteTask(id);
			
			 
			return getTask(principal,model,mav);
		}
	  @PostMapping("/task/insert") 
	  public String insertTask(@RequestParam(name = "comment",required = false) String comment,
			  @RequestParam(name = "limitday",required = false) String limitday
			  ,Principal principal,Model model,ModelAndView mav){
		  System.out.println(limitday);
		 
		  TaskData data = new TaskData();
		  
		  
		  //期限日をDate型に変換する 
		  Date sqlDate= Date.valueOf(limitday);
		  
		  data.setComment(comment); data.setLimitday(sqlDate);
		  data.setUser_id(principal.getName());
		  
		  log.info("「" + principal.getName() + "」insert comment:" + comment +
		  " limitday:" + data.getLimitday()); 
		  boolean isSuccess = taskService.setTask(data); 
		  if (isSuccess){
			  log.info("You are SUCSESS");
			  mav.addObject("isresult",true);
		  }else {
			  log.info("You are FAILED");
			  mav.addObject("isresult",false);
		  }
	  return getTask(principal,model,mav);
	
}
}
