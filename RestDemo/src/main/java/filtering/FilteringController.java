package filtering;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

@RestController
public class FilteringController {
	
	@GetMapping("/filtering")
	public MappingJacksonValue retriveSomeBean() {
		SomeBean someBean= new SomeBean("value1","value2","value3");
		MappingJacksonValue mapping =
				new MappingJacksonValue(someBean);
		SimpleBeanPropertyFilter filter=SimpleBeanPropertyFilter.filterOutAllExcept("field2","field3");
     SimpleFilterProvider filters=new SimpleFilterProvider().addFilter("filer", filter);
		mapping.setFilters(filters);
		return mapping;
	
	}
	@GetMapping("filteringAll")
	public MappingJacksonValue retriveAllBeans(){
		
		List<SomeBean> someBeans= Arrays.asList(new SomeBean("value1","value2","value3"),
			new SomeBean("value1","value2","value3"),
			new SomeBean("value1","value2","value3"));
		
		MappingJacksonValue mapping=
				new MappingJacksonValue(someBeans);
		SimpleBeanPropertyFilter filter=SimpleBeanPropertyFilter.filterOutAllExcept("field3");
		SimpleFilterProvider filters=new SimpleFilterProvider().addFilter("filer", filter);
		mapping.setFilters(filters);
		return mapping;
		
	}

}
