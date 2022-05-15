FROM openjdk:17

# Set user and group
USER root

# Create application user
RUN groupadd -g 1000 appuser
RUN useradd -u 1000 -g appuser -s /bin/sh -m appuser

# Copy binary into user location
COPY @project.build.finalName@-sb.jar /home/appuser

# Assume file ownership for app-user
RUN chown -R appuser:appuser /home/appuser
WORKDIR /home/appuser

# Switch to user
USER appuser

CMD ["java","-jar","/home/appuser/@project.build.finalName@-sb.jar"]
