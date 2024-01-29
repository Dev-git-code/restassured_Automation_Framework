package api.endpoints;


/* 
Swagger URI --> https://jobify-app-api-dev.onrender.com/api-docs/#/
Base URL : https://jobify-app-api-dev.onrender.com/api/v1

Register User (Post): base/auth/register
Login User (Post): base/auth/login
Delete User (Delete): base/auth/user/{email}
Create Job (Post): base/jobs
Get All Jobs (Get): base/jobs
Get Single Job (Get): base/jobs/{id}
Update Job (Patch): base/jobs/{id}
Delete Job (Delete): base/jobs/{id}

*/
public class JobifyRoutes {
	public static String baseUrl = "https://jobify-app-api-dev.onrender.com/api/v1";
	
	//Auth 
	public static String registerUrl = baseUrl + "/auth/register";
	public static String loginUrl = baseUrl + "/auth/login";
	public static String deleteUserUrl = baseUrl + "/auth/user/{email}";
	
	//Jobs
	public static String createJobUrl = baseUrl + "/jobs";
	public static String getAllJobsUrl = baseUrl + "/jobs";
	public static String getSingleJobUrl = baseUrl + "/jobs/{id}";
	public static String updateJobUrl = baseUrl + "/jobs/{id}";
	public static String deleteJobUrl = baseUrl + "/jobs/{id}";
	
	
}
