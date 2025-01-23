#!/bin/bash
# Укажите базовую директорию для поиска файлов
base_dir="/mnt/c/Users/nikit/out/src"
send_file() {
    local file_path="$1"
    local relative_path="src/${file_path#$base_dir/}"
    
    # Отправка файла через curl и сохранение ответа в переменной
    response=$(curl --location --silent --fail "192.168.0.104:9090/api/v1/addFile" --form "file=@${relative_path}")
    
    # Проверяем, успешен ли запрос
    if [ $? -eq 0 ]; then
        echo "Ответ от API: $response"
        # Здесь вы можете использовать переменную $response дальше в скрипте
    else
		echo "Ответ от API: $response"
    fi
}

# Проверяем, передан ли аргумент (путь к директории)
if [ -n "$1" ]; then
    target_dir="$1"
else
    target_dir="$base_dir"
fi

# Проверяем, существует ли директория
if [ ! -d "$target_dir" ]; then
    echo "Директория $target_dir не найдена."
    exit 1
fi
curl "192.168.0.104:9090/api/v1/setPackage"
echo
# Используем find для поиска всех файлов в целевой директории
find "$target_dir" -type f | while read -r file; do
    send_file "$file"
done



