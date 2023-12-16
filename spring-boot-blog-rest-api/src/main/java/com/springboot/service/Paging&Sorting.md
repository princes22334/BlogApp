JpaRepository internally uses Paging&SortingRepository 
it has 2 methods
1. Iterable<T> findAll(Sort sort)
2. Page<T> findAll(Pageable pageable)