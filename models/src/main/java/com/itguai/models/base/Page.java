package com.itguai.models.base;

import java.io.Serializable;
import java.util.List;

/**
 * Created by jianyuncheng on 15/4/30.
 */
public class Page<T>  implements Serializable {
    public List<T> content;
    public Boolean last;// true;
    public Integer totalPages;// 1;
    public Integer totalElements;// 1;
    public Boolean firstPage;// false;
    public Boolean lastPage;// true;
    public Integer numberOfElements;// 1;
    public Boolean first;// false;
    public Integer size;// 1;
    public Integer numb;//r: ;
}
