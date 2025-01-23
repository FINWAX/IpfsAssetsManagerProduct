# IPFS Assets Manager

Rest service that allows you to upload files to nodes and host them.

## Installation

1. Download the repository:
```
git clone https://github.com/FINWAX/IpfsAssetsManagerProduct
```
2. Change ENV variables in envFile

3. Build an image from a Dockerfile

Run the docker build command to create an image from your Dockerfile. Make sure you specify a tag (e.g. my-image) for your image:
```
docker build -t my-image .
```

Here -t my-image specifies the name (tag) for the image being created, and . specifies that the Dockerfile is in the current directory.

4. Run a container from the created image

Once the image has been successfully created, you can run the container using the docker run command. For example:
```
docker run -d --name my-container my-image
```

• -d runs the container in the background (detached mode).

• --name my-container sets the name for your container.

• my-image is the name of the image you created in the previous step.

5. Possible problems

Local IPFS Node can be launched on a random Swarm listening, it should be tracked, written to the env file (+1) and the container should be restarted 

6. Script.sh
   
The script automatically downloads all files of the required folder, which is specified by the path, change the base_dir variable to the absolute path link to the folder with the required files

example:
```
base_dir="/mnt/c/Users/nikit/out/src"
```
