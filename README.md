# IPFS Assets Manager

Rest service that allows you to upload files to nodes and host them.

## Installation

1. Download the repository:
```
git clone https://github.com/FINWAX/IpfsAssetsManagerProduct
```
2. Change ENV variables in envFile

IPFS_SCHEDULED_UPDATE_STATUS - frequency of node status updates
IPFS_SCHEDULED_UPLOAD_NODES - frequency of uploads to external nodes
IPFS_LOCAL_NODE -  Swarm listening on LocalNode
IPFS_TIMEOUT_REQUEST_FROM_NODE - External nodes wait time

4. UP container command:
```
docker-compose up
```

4. Possible problems

Local IPFS Node can be launched on a random Swarm listening, it should be tracked, written to the env file (+1) and the container should be restarted 

5. Script.sh
   
The script automatically downloads all files of the required folder, which is specified by the path, change the base_dir variable to the absolute path link to the folder with the required files

example:
```
base_dir="/mnt/c/Users/nikit/out/src"
```
