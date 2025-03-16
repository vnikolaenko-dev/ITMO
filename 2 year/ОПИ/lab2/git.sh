# Настройка: удаляем папку src, создаем её заново и инициализируем Git-репозиторий
rm -rf src
mkdir src
cd src
git init

# Переключение на пользователя Red
git config --local user.name red
git config --local user.email red@mail.com
echo "-- switched to Red User"

# Коммит 0: создаем первый коммит от пользователя Red
rm -rf *
cp -r ../commits/r0/* .
git add .
git commit --allow-empty -m "Revision r0"
echo "-- Commit r0"

# Переключение на пользователя Blue
git config --local user.name blue
git config --local user.email blue@mail.com
echo "-- switched to Blue User"

# Создание ветки branch1 и переключение на неё
git checkout -b branch1

# Коммит 1: создаем коммит от пользователя Blue в ветке branch1
rm -rf *
cp -r ../commits/r1/* .
git add .
git commit --allow-empty -m "Revision r1"
echo "-- Commit r1"

# Коммит 2: создаем коммит от пользователя Blue в ветке branch1
rm -rf *
cp -r ../commits/r2/* .
git add .
git commit --allow-empty -m "Revision r2"
echo "-- Commit r2"

# Создание ветки branch2 и переключение на неё
git checkout -b branch2

# Коммит 3: создаем коммит от пользователя Blue в ветке branch2
rm -rf *
cp -r ../commits/r3/* .
git add .
git commit --allow-empty -m "Revision r3"
echo "-- Commit r3"

# Переключение на ветку master
git checkout master

# Переключение на пользователя Red
git config --local user.name red
git config --local user.email red@mail.com
echo "-- switched to Red User"

# Коммит 4: создаем коммит от пользователя Red в ветке master
rm -rf *
cp -r ../commits/r4/* .
git add .
git commit --allow-empty -m "Revision r4"
echo "-- Commit r4"

# Переключение на ветку branch2
git checkout branch2

# Переключение на пользователя Blue
git config --local user.name blue
git config --local user.email blue@mail.com
echo "-- switched to Blue User"

# Коммит 5: создаем коммит от пользователя Blue в ветке branch2
rm -rf *
cp -r ../commits/r5/* .
git add .
git commit --allow-empty -m "Revision r5"
echo "-- Commit r5"

# Переключение на ветку branch1
git checkout branch1

# Коммит 6: создаем коммит от пользователя Blue в ветке branch1
rm -rf *
cp -r ../commits/r6/* .
git add .
git commit --allow-empty -m "Revision r6"
echo "-- Commit r6"

# Переключение на ветку master
git checkout master

# Переключение на пользователя Red
git config --local user.name red
git config --local user.email red@mail.com
echo "-- switched to Red User"

# Коммиты 7-10: создаем коммиты от пользователя Red в ветке master
rm -rf *
cp -r ../commits/r7/* .
git add .
git commit --allow-empty -m "Revision r7"
echo "-- Commit r7"

rm -rf *
cp -r ../commits/r8/* .
git add .
git commit --allow-empty -m "Revision r8"
echo "-- Commit r8"

rm -rf *
cp -r ../commits/r9/* .
git add .
git commit --allow-empty -m "Revision r9"
echo "-- Commit r9"

rm -rf *
cp -r ../commits/r10/* .
git add .
git commit --allow-empty -m "Revision r10"
echo "-- Commit r10"

# Переключение на ветку branch2
git checkout branch2

# Переключение на пользователя Blue
git config --local user.name blue
git config --local user.email blue@mail.com
echo "-- switched to Blue User"

# Коммит 11: создаем коммит от пользователя Blue в ветке branch2
rm -rf *
cp -r ../commits/r11/* .
git add .
git commit --allow-empty -m "Revision r11"
echo "-- Commit r11"

# Переключение на ветку master
git checkout master

# Переключение на пользователя Red
git config --local user.name red
git config --local user.email red@mail.com
echo "-- switched to Red User"

# Слияние ветки branch2 в master (без автоматического создания коммита)
# Либо ручками решаем конфликт - либо принимаем стратегию
git merge branch2 --no-commit --strategy=ours

# Коммит 12: создаем коммит слияния от пользователя Red
git commit --allow-empty -m "Merge branch2 into master"
echo "-- Commit r12"

# Переключение на ветку branch1
git checkout branch1

# Переключение на пользователя Blue
git config --local user.name blue
git config --local user.email blue@mail.com
echo "-- switched to Blue User"

# Коммит 13: создаем коммит от пользователя Blue в ветке branch1
rm -rf *
cp -r ../commits/r13/* .
git add .
git commit --allow-empty -m "Revision r13"
echo "-- Commit r13"

# Переключение на ветку master
git checkout master

# Переключение на пользователя Red
git config --local user.name red
git config --local user.email red@mail.com
echo "-- switched to Red User"

# Слияние ветки branch1 в master (без автоматического создания коммита)
# Либо ручками решаем конфликт - либо принимаем стратегию
git merge branch1 --no-commit --strategy=ours

# Коммит 14: создаем коммит слияния от пользователя Red
git commit --allow-empty -m "Merge branch1 into master"
echo "-- Commit r14"

# Вывод графа коммитов
git log --graph --abbrev-commit --decorate --format=format:'%C(bold blue)%h%C(reset) - %C(bold cyan)%an%C(reset) %C(bold green)(%ar)%C(reset) %C(white)%s%C(reset) %C(bold yellow)%d%C(reset)' --all