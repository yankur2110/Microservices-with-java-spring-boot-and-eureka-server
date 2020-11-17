# Microservices-with-java-spring-boot-and-eureka-server
Here we are creating 3 microservices that interacts amongst themselves using Netflix's eureka server in load balanced way.


Project(steps):
Project(steps):
AIM: to create 3 microservices admission, hr and pathology. Then call hr/pathology service from admission service and fetch the data.
first we will achieve this by calling the rest endpoint where we use the port number at which hr/pathology services are running.
then we will use eureka discovery server, which acts like a load balancer (route to different services based on the request coming). Here we don’t need to remember the port number to call the hr/pathology-service, instead we just need to remember their names/
 create 3 applications using spring intializer (we will use STS as IDE, download it):
admissions-service
group is like base package where everything resides (here com.kindsonthegenius)
artifact is simply the name of project (here admission-service).
java 11.
spring-boot 2.4.0
dependency: Spring Web (Build web, including RESTful, applications using Spring MVC. Uses Apache Tomcat as the default embedded container.)
hr-service //repeat
pathology-service //repeat 
import the projects to STS and set port and application name for all three services in their respective application.properties file(present inside src/main/resources directory):
server.port=8081
spring.application.name=admissions-service //similarly 8082,8083 for hr/pathology service.
create resources package and models package for each service.
admissions-service:
//here we will create 4 methods getPhysicians(), getDiseases(), getPatients(), getPatientById().  First two, internally calls the hr/pathology service and fetch data. Last two returns the list of patients created hard-coded inside the AdmissionResource class itself.
create AdmissionResource class inside resources package. This will be the controller class.
add @RestController,@RequestMapping("/admissions") annotation to it. 
create Patient class inside models package. 
create hardcoded patients. Add request mapping to getPatients and getPatiensById methods. Return the whole list of patients and a patient by Id respectively.
    List<Patient> patients = Arrays.asList(
            new Patient("P1","Ramadhir", "Indian"),
            new Patient("P2","Christopher", "American"),
            new Patient("P3", "Irina", "England")
            );
    
    @RequestMapping("/patients")
    List<Patient> getPatients(){
        return patients;
    }
    
    @RequestMapping("/patients/{Id}")
    Patient getPatientById(@PathVariable("Id") String Id) {
        
        Patient p = patients.stream()
                .filter(patient -> Id.equals(patient.getId()))
                .findAny()
                .orElse(null);
        return p;
        
    }
hr-service/pathology-service:
create Employee/Disease class inside model package and HrResource/PathologyResource inside resource package.
create getEmployees(),getEmployeeById()/getDiseases(),getDiseaseById() like previous step inside HrResource/PathologyREsource.
test all the services on browser, like localhost:8081/admissions/patients. It will return you list of patients.
now comes the core part of project, call hr/pathology service from admissions service. To do so:
In ApplicationResource.java, we will use RestTemplate class provided by spring. It is used for making synchronous HTTP request on the client side.
@Autowired
private RestTemplate restTemplate;
to autowire RestTemplate we need to need create the bean of RestTemplate at the start of application. So, in AdmissionsServiceApplication main class(@SpringBootApplication), we will use @bean annotation as follows:
@Bean
public RestTemplate restTemplate(){
   return new RestTemplate();
}
we will now complete getPhysicians() method in ApplicationResouce.java to get list of employees from hr service.
//here note that we are modifying the hr service’s getEmployees method (HrResouce.java. Now it is not returning List<Employee>  but EmployeesList(an model class) which internally has List<Employee> as it’s data member).
//since we will receive the response in EmployeesList object, we need to create same EmployeesList class inside application-service’s model package.
/*Creating EmployeesList model class and modifying getEmployees method of HrResouces. */
@RequestMapping("/employees")
public EmployeesList getEmployees(){
   EmployeesList employeesList = new EmployeesList();
   employeesList.setEmployeesList(employees);
   return employeesList;
}


@RequestMapping("/physicians")
    public EmployeesList getPhysicians() {
        EmployeesList employeesList = restTemplate.getForObject("http://localhost:8082/hr/employees", EmployeesList.class);
        /*here it's important to note we are calling hr-microservice using port 8082*/
        return employeesList;
    }
    
similarly complete getDiseases method inside ApplicationResource.java.
now we will build Netflix’s eureka server (a service discovery pattern). To do so, create a spring project using spring initializr:
give groupId(com.kindsonthegenius) and artifactId(project name..discovery-server).
add dependency Eureka Server.
import the project.
Add @EnableEurekaServer to the main class(@SpringBootApplication annotated class, present in src/main/java directory) of discovery-server.
add following to application.properties
server.port=8761
eureka.client.register-with-eureka=false
eureka.client.fetch-registry=false

/* normally when we create a eureka server, it tries to find other eureka server and once it finds it register itself as a client so they kind of become the client and server at the same time.
so using this property we are telling that no other server is avaialable. Letting it not discover any other server.*/
test the eureka server on localhost:8761
now we will create eureka client, goto ApplicationResource, HrResource, PathologyResource and add @EnableEurekaClient.
to publish eureka client to server:
make sure each client (our 3 services) has following dependency/property in pom.xml file:
<dependency>
   <groupId>org.springframework.cloud</groupId>
   <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
</dependency>

property:
<spring-cloud.version>2020.0.0-SNAPSHOT</spring-cloud.version>
make sure our server(discovery server) has following dependency in it’s pom.xml file:
<dependency>
   <groupId>org.springframework.cloud</groupId>
   <artifactId>spring-cloud-starter-netflix-eureka-server</artifactId>
</dependency>
/* it comes automatically when you add eureka-server as dependency while creating the project*/
when you update your maven project, you can your dependencies (as jar files).
add the @LoadBalanced annotation to RestTemplate bean:
@Bean
@LoadBalanced
public RestTemplate restTemplate(){
   return new RestTemplate();
}
final step is to replace the url’s which we used as first parameter in restTemplate.getForObject(). We will replace the port numbers to respective service name as follows:
restTemplate.getForObject("http://hr-service/hr/employees", EmployeesList.class);
run the eureka server on port 8761, you will see these services registered. Now, just call the application-server methods(like below url) which indirectly call to hr/pathology service. It will work fine.
localhost:8081/admissions/physicians

-----------------------------------------------------------------------------------------END--------------------------------------------------------------------------------------

