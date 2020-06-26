package com.clip.challenge.builder;

public interface Builder<V, T> {

    /**
     * Builds an Entity object using the provided DTO object.
     *
     * @param dto object
     * @return entity object
     */
    V buildEntity(T dto);

    /**
     * Builds a DTO object using the provided Entity object.
     *
     * @param entity object
     * @return dto object
     */
    T buildDto(V entity);

}
