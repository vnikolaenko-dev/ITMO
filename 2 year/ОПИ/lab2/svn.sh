# Настройка: удаляем папку src, создаем её заново и инициализируем SVN-репозиторий
rm -rf src
mkdir src
cd src
svnadmin create repo
svn checkout file://$(pwd)/repo working_copy
cd working_copy

# Создание стандартной структуры SVN (trunk, branches, tags)
svn mkdir trunk branches tags -m "Creating standard SVN structure"

# Переключение на пользователя Red
export SVN_USERNAME=red
export SVN_PASSWORD=red

# Ревизия 0: создаем первую ревизию от пользователя Red
rm -rf *
cp -r ../../commits/r0/* .
svn add *
svn commit -m "Revision r0" --username $SVN_USERNAME --password $SVN_PASSWORD
echo "-- Revision r0"

# Переключение на пользователя Blue
export SVN_USERNAME=blue
export SVN_PASSWORD=blue

# Создание ветки branch1
svn copy trunk branches/branch1 -m "Creating branch1" --username $SVN_USERNAME --password $SVN_PASSWORD
svn switch file://$(pwd)/repo/branches/branch1

# Ревизия 1: создаем ревизию от пользователя Blue в ветке branch1
rm -rf *
cp -r ../../commits/r1/* .
svn add *
svn commit -m "Revision r1" --username $SVN_USERNAME --password $SVN_PASSWORD
echo "-- Revision r1"

# Ревизия 2: создаем ревизию от пользователя Blue в ветке branch1
rm -rf *
cp -r ../../commits/r2/* .
svn add *
svn commit -m "Revision r2" --username $SVN_USERNAME --password $SVN_PASSWORD
echo "-- Revision r2"

# Создание ветки branch2
svn copy trunk branches/branch2 -m "Creating branch2" --username $SVN_USERNAME --password $SVN_PASSWORD
svn switch file://$(pwd)/repo/branches/branch2

# Ревизия 3: создаем ревизию от пользователя Blue в ветке branch2
rm -rf *
cp -r ../../commits/r3/* .
svn add *
svn commit -m "Revision r3" --username $SVN_USERNAME --password $SVN_PASSWORD
echo "-- Revision r3"

# Переключение на trunk (аналог master в Git)
svn switch file://$(pwd)/repo/trunk

# Переключение на пользователя Red
export SVN_USERNAME=red
export SVN_PASSWORD=red

# Ревизия 4: создаем ревизию от пользователя Red в trunk
rm -rf *
cp -r ../../commits/r4/* .
svn add *
svn commit -m "Revision r4" --username $SVN_USERNAME --password $SVN_PASSWORD
echo "-- Revision r4"

# Переключение на ветку branch2
svn switch file://$(pwd)/repo/branches/branch2

# Переключение на пользователя Blue
export SVN_USERNAME=blue
export SVN_PASSWORD=blue

# Ревизия 5: создаем ревизию от пользователя Blue в ветке branch2
rm -rf *
cp -r ../../commits/r5/* .
svn add *
svn commit -m "Revision r5" --username $SVN_USERNAME --password $SVN_PASSWORD
echo "-- Revision r5"

# Переключение на ветку branch1
svn switch file://$(pwd)/repo/branches/branch1

# Ревизия 6: создаем ревизию от пользователя Blue в ветке branch1
rm -rf *
cp -r ../../commits/r6/* .
svn add *
svn commit -m "Revision r6" --username $SVN_USERNAME --password $SVN_PASSWORD
echo "-- Revision r6"

# Переключение на trunk
svn switch file://$(pwd)/repo/trunk

# Переключение на пользователя Red
export SVN_USERNAME=red
export SVN_PASSWORD=red

# Ревизии 7-10: создаем ревизии от пользователя Red в trunk
rm -rf *
cp -r ../../commits/r7/* .
svn add *
svn commit -m "Revision r7" --username $SVN_USERNAME --password $SVN_PASSWORD
echo "-- Revision r7"

rm -rf *
cp -r ../../commits/r8/* .
svn add *
svn commit -m "Revision r8" --username $SVN_USERNAME --password $SVN_PASSWORD
echo "-- Revision r8"

rm -rf *
cp -r ../../commits/r9/* .
svn add *
svn commit -m "Revision r9" --username $SVN_USERNAME --password $SVN_PASSWORD
echo "-- Revision r9"

rm -rf *
cp -r ../../commits/r10/* .
svn add *
svn commit -m "Revision r10" --username $SVN_USERNAME --password $SVN_PASSWORD
echo "-- Revision r10"

# Переключение на ветку branch2
svn switch file://$(pwd)/repo/branches/branch2

# Переключение на пользователя Blue
export SVN_USERNAME=blue
export SVN_PASSWORD=blue

# Ревизия 11: создаем ревизию от пользователя Blue в ветке branch2
rm -rf *
cp -r ../../commits/r11/* .
svn add *
svn commit -m "Revision r11" --username $SVN_USERNAME --password $SVN_PASSWORD
echo "-- Revision r11"

# Переключение на trunk
svn switch file://$(pwd)/repo/trunk

# Переключение на пользователя Red
export SVN_USERNAME=red
export SVN_PASSWORD=red

# Слияние ветки branch2 в trunk (вручную разрешаем конфликты)
svn merge file://$(pwd)/repo/branches/branch2
# Если есть конфликты, SVN остановится и попросит их разрешить вручную.
# После разрешения конфликтов:
svn resolve --accept working -R .
svn commit -m "Merge branch2 into trunk (auto-resolve conflicts)" --username $SVN_USERNAME --password $SVN_PASSWORD
echo "-- Revision r12 (Merge branch2 into trunk)"

# Переключение на ветку branch1
svn switch file://$(pwd)/repo/branches/branch1

# Переключение на пользователя Blue
export SVN_USERNAME=blue
export SVN_PASSWORD=blue

# Ревизия 13: создаем ревизию от пользователя Blue в ветке branch1
rm -rf *
cp -r ../../commits/r13/* .
svn add *
svn commit -m "Revision r13" --username $SVN_USERNAME --password $SVN_PASSWORD
echo "-- Revision r13"

# Переключение на trunk
svn switch file://$(pwd)/repo/trunk

# Переключение на пользователя Red
export SVN_USERNAME=red
export SVN_PASSWORD=red

# Слияние ветки branch1 в trunk (вручную разрешаем конфликты)
svn merge file://$(pwd)/repo/branches/branch1
# Если есть конфликты, SVN остановится и попросит их разрешить вручную.
# После разрешения конфликтов:
svn resolve --accept working -R .
svn commit -m "Merge branch1 into trunk (auto-resolve conflicts)" --username $SVN_USERNAME --password $SVN_PASSWORD
echo "-- Revision r14 (Merge branch1 into trunk)"

# Вывод истории ревизий
svn log -v