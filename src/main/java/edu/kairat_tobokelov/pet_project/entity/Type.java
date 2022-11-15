package edu.kairat_tobokelov.pet_project.entity;

import edu.kairat_tobokelov.pet_project.dto.form.RegisterRequest;

public enum Type {
    ROLE_ADMIN {
        @Override
        public void castByType(RegisterRequest registerRequest) throws IllegalArgumentException {
        }
    },
    ROLE_MENTOR {
        @Override
        public void castByType(RegisterRequest registerRequest) throws IllegalArgumentException {
            var experience = registerRequest.getType();
            var audience = registerRequest.getAudience();
            if (experience == null || audience == null)
                throw new IllegalArgumentException("Experience and audience are required!");
        }
    },
    ROLE_USER {
        @Override
        public void castByType(RegisterRequest registerRequest) throws IllegalArgumentException {
            registerRequest.setAudience(null);
            registerRequest.setExperience(null);
        }
    };

    public abstract void castByType(RegisterRequest registerRequest) throws IllegalArgumentException;
}
